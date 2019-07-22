package com.laity.backstage.system.config;

import com.alibaba.druid.support.http.StatViewServlet;

import com.alibaba.druid.support.http.WebStatFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

import org.springframework.boot.web.servlet.ServletRegistrationBean;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

/**
 * @author D.F Douglas
 * @version 1.0.0
 * @ClassName DruidConfig
 * @Description 监控服务器
 * @createTime 2019/6/5/17:49
 */
@Configuration
public class DruidConfig {

    private static final Logger logger = LoggerFactory.getLogger(DruidConfig.class);
    @Bean
    public ServletRegistrationBean druidServlet() {

        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        //ip 白名单
        //servletRegistrationBean.addInitParameter("allow","210.26.116.158");
        //ip 黑名单
        //servletRegistrationBean.addInitParameter("deny","192.116.1.1");
        //控制台管理的账号密码.
        servletRegistrationBean.addInitParameter("loginUsername", "admin");

        servletRegistrationBean.addInitParameter("loginPassword", "123456");

        //是否能够重置数据，false 为禁用Html 页面上的Rest all 功能
//        servletRegistrationBean.addInitParameter("resetEnable","false");
        logger.info("druid 正在运行中-->druidServlet()");
        return servletRegistrationBean;

    }


    @Bean

    public FilterRegistrationBean filterRegistrationBean() {

        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();

        filterRegistrationBean.setFilter(new WebStatFilter());

        filterRegistrationBean.addUrlPatterns("/*");

        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");

        logger.info("druid --》filterRegistrationBean()");
        return filterRegistrationBean;

    }
}
