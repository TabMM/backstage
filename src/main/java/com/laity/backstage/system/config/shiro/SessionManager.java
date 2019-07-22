package com.laity.backstage.system.config.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * @author D.F Douglas
 * @version 1.0.0
 * @ClassName SessionManager
 * @Description （在shiro配置中会用到）自定义session 管理器
 * https://blog.csdn.net/u013615903/article/details/78781166
 * @createTime 2019/6/8/14:03
 */
public class SessionManager extends DefaultWebSessionManager {
    private static final Logger logger = LoggerFactory.getLogger(SessionManager.class);
    private static final String AUTHORIZATION ="Authorization";
    private static final String REFERENCED_SESSION_ID_SOURCE="Stateless request";
    public SessionManager(){
        super();
    }

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        String id = WebUtils.toHttp(request).getHeader(AUTHORIZATION);
        logger.info("SessionManager---->请求头id："+id);
        //如果请求头中有 Authorization 则其值为SessionId
        if(!StringUtils.isEmpty(id)){
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return id;
        }else {
            logger.info(""+request+response);
            //否则按默认规则从cookie取sessionId
            return super.getSessionId(request, response);
        }

    }
}
