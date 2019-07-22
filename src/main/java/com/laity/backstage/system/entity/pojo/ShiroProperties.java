package com.laity.backstage.system.entity.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author D.F Douglas
 * @version 1.0.0
 * @ClassName ShiroProperties
 * @Description TODO
 * @createTime 2019/6/4/16:21
 */
@Component
//@PropertySource(value = "classpath:shiro.yml",encoding = "utf-8",factory = YmlFactory.class)
//@ConfigurationProperties(prefix = "laity.shiro")
public class ShiroProperties {
    @Value("${laity.shiro.expireIn}")
    private long expireIn;
    @Value("${laity.shiro.sessionTimeout}")
    private long sessionTimeout;
    @Value("${laity.shiro.cookieTimeout}")
    private long cookieTimeout;
    @Value("${laity.shiro.anonUrl}")
    private String anonUrl;
    @Value("${laity.shiro.loginUrl}")
    private String loginUrl;
    @Value("${laity.shiro.successUrl}")
    private String successUrl;
    @Value("${laity.shiro.logoutUrl}")
    private String logoutUrl;
    @Value("${laity.shiro.unauthorizedUrl}")
    private String unauthorizedUrl;
    @Value("${laity.shiro.sessionIdName}")
    private String sessionIdName;

    public long getExpireIn() {
        return expireIn;
    }

    public long getSessionTimeout() {
        return sessionTimeout;
    }

    public long getCookieTimeout() {
        return cookieTimeout;
    }

    public String getAnonUrl() {
        return anonUrl;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public String getLogoutUrl() {
        return logoutUrl;
    }

    public String getUnauthorizedUrl() {
        return unauthorizedUrl;
    }

    public String getSessionIdName() {
        return sessionIdName;
    }

    @Override
    public String toString() {
        return "ShiroProperties{" +
                "expireIn='" + expireIn + '\'' +
                ", sessionTimeout='" + sessionTimeout + '\'' +
                ", cookieTimeout='" + cookieTimeout + '\'' +
                ", anonUrl='" + anonUrl + '\'' +
                ", loginUrl='" + loginUrl + '\'' +
                ", successUrl='" + successUrl + '\'' +
                ", logoutUrl='" + logoutUrl + '\'' +
                ", unauthorizedUrl='" + unauthorizedUrl + '\'' +
                ", sessionIdName='" + sessionIdName + '\'' +
                '}';
    }
}
