package com.sucl.sbjms.security.service;

import com.sucl.sbjms.security.token.UserToken;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.OrderComparator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author sucl
 * @date 2019/4/16
 */
@Service
public class TokenCheckServiceImpl implements TokenCheckService ,ApplicationContextAware {
    private List<TokenCheckStrategy> tokenCheckStrategys;

    @Override
    public boolean doCheck(UserToken token) {
        if(tokenCheckStrategys!=null){
            tokenCheckStrategys.stream().forEach(cs ->cs.doCheck(token));
        }
        return true;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        initTokenCheckStrategy(applicationContext);
    }

    private void initTokenCheckStrategy(ApplicationContext applicationContext) {
        Map<String, TokenCheckStrategy> tokenCheckStrategyMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, TokenCheckStrategy.class, true, false);
        if(MapUtils.isNotEmpty(tokenCheckStrategyMap)){
            this.tokenCheckStrategys = new ArrayList<>(tokenCheckStrategyMap.values());
            tokenCheckStrategys.sort(OrderComparator.INSTANCE);
        }
    }
}
