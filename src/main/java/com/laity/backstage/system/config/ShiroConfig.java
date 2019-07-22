package com.laity.backstage.system.config;


import com.laity.backstage.system.config.shiro.SessionManager;
import com.laity.backstage.system.config.shiro.ShiroRealm;
import com.laity.backstage.system.entity.pojo.RedisProperties;
import com.laity.backstage.system.entity.pojo.ShiroProperties;
import com.laity.backstage.system.exception.MyExceptionHandler;
import com.laity.backstage.system.service.MenuService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.LinkedHashMap;


/**
 * @author D.F Douglas
 * @version 1.0.0
 * @ClassName ShiroConfig
 * @Description shiro的核心配置类 shiro的所有初始化bean都在这个类中操作，各个bean我在下面都会做详细的注释，帮助理解
 * @createTime 2019/6/4/11:32
 */
@Configuration
public class ShiroConfig {
    private static final Logger logger = LoggerFactory.getLogger(ShiroConfig.class);
    //部分配置文件，详细见shiro.yml
    @Autowired
    ShiroProperties shiroProperties;
    @Autowired
    RedisProperties redisProperties;
    @Autowired
    MenuService menuService;

    /*
     * @Author  D.F Douglas
     * @Description //ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：单独一个ShiroFilterFactoryBean配置是会报错的，
     * 因为在初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     * Filter Chain定义说明
     1、一个URL可以配置多个Filter，使用逗号分隔
     2、当设置多个过滤器时，全部验证通过，才视为通过
     3、部分过滤器可指定参数，如perms，roles
     * @Date 14:40 2019/6/7
     * @Param [securityManager]
     * @return org.apache.shiro.spring.web.ShiroFilterFactoryBean
     **/
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        logger.info("权限配置-->ShiroConfig.shiroFilterFactoryBean()");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //设置自定义fifter
//        shiroFilterFactoryBean.getFilters().put(DefaultFilter.authc.name(), new FormAuthenticationFilter());

        //设置securityManager(必须)
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        logger.info("设置securityManager完成"+securityManager);
        //登录的url 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl(shiroProperties.getLoginUrl());
        logger.info("登录的url"+shiroProperties.getLoginUrl());
        //登录成功后跳转的url
        shiroFilterFactoryBean.setSuccessUrl(shiroProperties.getSuccessUrl());
        logger.info("登录成功后跳转的url"+shiroProperties.getSuccessUrl());
        //未授权的url
        shiroFilterFactoryBean.setUnauthorizedUrl(shiroProperties.getUnauthorizedUrl());
        logger.info("未授权的url"+shiroProperties.getUnauthorizedUrl());
        //这里配置授权链(拦截器)
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        //设置免认证
        String[] anonUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(shiroProperties.getAnonUrl(), ",");

        // 配置退出过滤器，其中具体的退出代码 Shiro已经替我们实现了
        filterChainDefinitionMap.put(shiroProperties.getLogoutUrl(), "logout");
        logger.info("登出路径："+shiroProperties.getLogoutUrl());
        //过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        for (String url : anonUrls) {
            logger.info("允许访问路径："+url);
            filterChainDefinitionMap.put(url, "anon");
        }
        // 除上以外所有 url都必须认证通过才可以访问，未通过认证自动访问 LoginUrl
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /*
     * @Author  D.F Douglas
     * @Description //凭证匹配器
     * 由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     * 所以我们需要修改下doGetAuthenticationInfo中的代码;
     * @Date 15:09 2019/6/7
     * @Param []
     * @return org.apache.shiro.authc.credential.HashedCredentialsMatcher
     **/
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //散列算法：这里使用Md5算法
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        //散列的次数，2代表两次，相当于MD5(MD5("xxx"))
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }

    @Bean
    public ShiroRealm shiroRealm() {
        ShiroRealm shiroRealm = new ShiroRealm();
        //设置凭证匹配器
        shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return shiroRealm;
    }

    /*
     * @Author  D.F Douglas
     * @Description // 配置各种manager,跟xml的配置很像，但是，这里有一个细节，就是各个set的次序不能乱
     * @Date 17:20 2019/6/4
     * @Param [realm, template]
     * @return java.lang.SecurityManager
     **/
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置Realm
        securityManager.setRealm(shiroRealm());
        //自定义缓存实现 使用redis
        securityManager.setCacheManager(cacheManager());
        //自定义 Session管理 使用redis
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    @Bean
    public SessionManager sessionManager() {
        SessionManager sessionManager = new SessionManager();
        // 网上各种说要自定义sessionDAO 其实完全不必要，shiro自己就自定义了一个，可以直接使用，还有其他的DAO，自行查看源码即可
//        sessionManager.setSessionDAO(new EnterpriseCacheSessionDAO());
        sessionManager.setSessionDAO(redisSessionDAO());
        //设置session超时时间，单位为毫秒
        sessionManager.setGlobalSessionTimeout(shiroProperties.getSessionTimeout());
        sessionManager.setSessionIdCookie(new SimpleCookie(shiroProperties.getSessionIdName()));
        return sessionManager;
    }

    /*
     * @Author  D.F Douglas
     * @Description //cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     * @Date 15:36 2019/6/7
     * @Param []
     * @return org.crazycake.shiro.RedisCacheManager
     **/
    public RedisCacheManager cacheManager() {
        RedisCacheManager manager = new RedisCacheManager();
        manager.setRedisManager(redisManager());
        //配置过期时间
        manager.setExpire(18000);
        return manager;
    }

    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(redisProperties.getHost());
        redisManager.setPort(redisProperties.getPort());
        redisManager.setTimeout(redisProperties.getTimeout());
        //配置过期时间
        //  redisManager.setExpire();
        return redisManager;
    }

    /*
     * @Author  D.F Douglas
     * @Description //开启shiro aop 注解支持
     * 使用代理方式;所以需要开启代码支持
     * @Date 15:17 2019/6/7
     * @Param [securityManager]
     * @return org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
     **/
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor sourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        sourceAdvisor.setSecurityManager(securityManager);
        return sourceAdvisor;
    }

    /*
     * @Author  D.F Douglas
     * @Description //RedisSessionDAO shiro sessionDao层的实现 通过redis
     * @Date 15:42 2019/6/7
     * @Param []
     * @return org.crazycake.shiro.RedisSessionDAO
     **/
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO sessionDAO = new RedisSessionDAO();
        sessionDAO.setRedisManager(redisManager());
        return sessionDAO;
    }

    @Bean(name = "exceptionHandler")
    public HandlerExceptionResolver handlerExceptionResolver(){
        return new MyExceptionHandler();
    }


}
