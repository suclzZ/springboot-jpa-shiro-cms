package com.sucl.sbjms.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sucl.sbjms.core.rem.Message;
import com.sucl.sbjms.security.Constant;
import com.sucl.sbjms.security.token.DefaultToken;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.util.WebUtils;
import sun.security.krb5.Realm;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 登录拦截器
 * 如果交给spring管理则会出现问题
 * 如果将filter交由spring管理，那么会对所有请求过滤（而在过滤的过程中必然会依赖于shiro的一些对象，比如SecurityManager，但是这个对象很可能还没有构建出来）
 * 然后出现异常了：UnavailableSecurityManagerException: No SecurityManager accessible to the calling code, either bound to the org.apache.shiro.util.ThreadContext or as a vm static singleton.  This is an invalid application configuration.
 * 但是shiro的过滤器是针对我们的配置进行指向性过滤
 *
 * filter->token->realm->account
 *
 * @author sucl
 * @date 2019/4/12
 */
//@Component("formAuthc")
public class DefaultFormAuthenticationFilter extends FormAuthenticationFilter {
    private static final String LOGIN_TYPE_FORM="01";
    private static final String LOGIN_TYPE_AJAX="02";
    private static final String LOGIN_TYPE_TOKEN="03";

    private static final String PARAM_LOGIN_TYPE="loginType";
    private static final String PARAM_VERIFICATION_CODE="verificationCode";

    private String verificationCode;//验证码
    private boolean verifyCheck = true;//是否启用验证码校验
    private String DefaultLoginType = LOGIN_TYPE_AJAX;//登录类型

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        DefaultToken token = new DefaultToken(getUsername(request),getPassword(request),isRememberMe(request),getHost(request));
        if(verifyCheck){
            verificationCode = getVerificationCode(request);
            if(!StringUtils.isEmpty(verificationCode) && verificationCode.equals(getSessionVerificationCode(request))){
                token.setVerification(true);
            }
        }
        return  token;
    }

    protected String getVerificationCode(ServletRequest request){
        return WebUtils.findParameterValue(request,PARAM_VERIFICATION_CODE);
    }

    protected String getSessionVerificationCode(ServletRequest request){
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        Object verCode = session.getAttribute(Constant.KAPTCHA_SESSION_KEY);
        return verCode==null?null:verCode.toString();
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        configSession(request,response);
        return super.isAccessAllowed(request, response, mappedValue) || getSubject(request, response).isRemembered();
    }

    protected void configSession(ServletRequest request,ServletResponse response){
        HttpSession session = ((HttpServletRequest) request).getSession();
        Object principal = getSubject(request, response).getPrincipal();
        if(principal!=null){
            session.setAttribute("username",principal.toString());
        }
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        configSession(request,response);
        String loginType = getLoginType(request);
        if(LOGIN_TYPE_AJAX.equals(loginType)){
            try(ServletOutputStream out = response.getOutputStream()){
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.writeValue(out,new Message(Message.SUCCESS_CODE,""));
            }catch (IOException e1) {
                e1.printStackTrace();
            }
            return false;
        }else if(LOGIN_TYPE_TOKEN.equals(equals(loginType))){
            //创建token
            return false;
        }else{
            return super.onLoginSuccess(token, subject, request, response);
        }
    }

    protected String getLoginType(ServletRequest request){
        String loginType = WebUtils.findParameterValue(request, PARAM_LOGIN_TYPE);
        return StringUtils.isEmpty(loginType)?DefaultLoginType:loginType;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        String info = loginFailure(e);
        String loginType = getLoginType(request);
        if(LOGIN_TYPE_AJAX.equals(loginType) ||LOGIN_TYPE_TOKEN.equals(equals(loginType))){
            try(ServletOutputStream out = response.getOutputStream()){
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.writeValue(out,new Message(Message.FAILURE_CODE,info));
            }catch (IOException e1) {
                e1.printStackTrace();
            }
            return false;
        }else if(LOGIN_TYPE_FORM.equals(equals(WebUtils.findParameterValue(request,PARAM_LOGIN_TYPE)))){
            request.setAttribute("error",info);
            return true;
        }
        return super.onLoginFailure(token, e, request, response);
    }

    private String loginFailure(AuthenticationException e) {
        String msg = e.getMessage();
        if(e instanceof AccountException){
            msg = "账号异常！";
            if(e instanceof DisabledAccountException){
                msg = "账号不可用！";
                if(e instanceof LockedAccountException){
                    msg = "账号已锁定！";
                }
            }else if(e instanceof ConcurrentAccessException){
                msg = "多用户登录异常！";
            }else if(e instanceof  ExcessiveAttemptsException){
                msg = "账号认证失败次数过多！";
            }else if(e instanceof UnknownAccountException){
                msg = "未知的账号！";
            }
        }else if(e instanceof CredentialsException){
            msg = "密码异常！";
            if(e instanceof IncorrectCredentialsException){
                msg = "密码不正确！";
            }else if(e instanceof ExpiredCredentialsException){
                msg = "密码过期！";
            }
        }else if(e instanceof UnsupportedTokenException){
            msg = "身份错误！";
        }
        return msg;
    }
}
