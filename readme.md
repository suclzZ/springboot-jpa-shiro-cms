前端借鉴[Layui-admin](https://github.com/MrMoveon/layuiAdmin)


###### 在springboot-jpa-ms基础上增加了shiro，主要完成了认证、授权
###### 页面通过freemaker模板处理

###### 
    shiro多token：
    用户实体 实现IUser
    新建token 继承UsernamePasswordToken
    新建filter 继承FormAuthenticationFilter
    新建Account 继承AbstractGenericAccount
    新建用户相关服务 实现PrincipalAdapter
    配置新的filter指向token
    
    request -> filter -> token -> realm -> PrincipalAdapter ->account 
    主要是多表用户认证
    
    多属性认证：
    知道属性上添加@Principal