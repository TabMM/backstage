package com.laity.backstage.system.config.shiro;

import com.laity.backstage.system.entity.Menu;
import com.laity.backstage.system.entity.User;
import com.laity.backstage.system.service.MenuService;
import com.laity.backstage.system.service.SysUserService;
import com.laity.backstage.system.utils.PasswordUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.ByteSource;
import org.crazycake.shiro.RedisSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author D.F Douglas
 * @version 1.0.0
 * @ClassName ShiroRealm
 * @Description 登录授权
 * @createTime 2019/6/4/18:20
 */
public class ShiroRealm extends AuthorizingRealm {
    private static final Logger logger = LoggerFactory.getLogger(ShiroRealm.class);
    @Autowired
    private SysUserService userService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private RedisSessionDAO redisSessionDAO;

    /*
     * @Author  D.F Douglas
     * @Description //授权
     * @Date 15:58 2019/6/7
     * @Param [authenticationToken]
     * @return org.apache.shiro.authc.AuthenticationInfo
     **/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("权限配置授权-->ShiroRealm.doGetAuthorizationInfo()");
        //权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
        SimpleAuthorizationInfo info =new SimpleAuthorizationInfo();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        logger.info("User信息---->UserId:"+user.getUserId());
        Map<String, Object> map = new HashMap<>();
        map.put("userid",user.getUserId());
        List<Menu> menuList = menuService.lodUserMenu(map);

        for (Menu r : menuList) {
            logger.info("权限菜单----》菜单id："+r.getMenuId()+"菜单名称："+r.getName()+"菜单路径："+r.getUrl());
            info.addStringPermission(r.getUrl());
        }
        return info;
    }

    /*
     * @Author  D.F Douglas
     * @Description //认证
     * @Date 16:10 2019/6/7
     * @Param [authenticationToken]
     * @return org.apache.shiro.authc.AuthenticationInfo
     **/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("权限配置认证-->ShiroRealm.doGetAuthenticationInfo()");
        User info =new User();
        info.setUsername("ldf");
        info.setPassword("ldf");
        //获取用户输入的账号
        info.setUsername((String)authenticationToken.getPrincipal());
        logger.info("正在尝试的账号:"+info.getUsername());
        User user =userService.selectByUsername(info);
        if (null==user)throw new UnknownAccountException();
        logger.info("正在验证的账号:"+info.getUsername());
        String pwd = PasswordUtils.encryptPwd((char[]) authenticationToken.getCredentials(),info.getUsername());
        if (!(pwd.equals(user.getPassword())))throw new IncorrectCredentialsException();
        logger.info("正在验证账号的密码");
        //账户锁定或禁用
        if (0==user.getStatus())throw new LockedAccountException();
        logger.info("账号认证成功:"+info.getUsername());
        SimpleAuthenticationInfo authorizationInfo =new SimpleAuthenticationInfo(user,user.getPassword(), ByteSource.Util.bytes(user.getUsername()),getName());
        //当通过验证后，把用户信息放在Session里
        logger.info("正在存储此账户信息:"+info.getUsername());
        Session session =SecurityUtils.getSubject().getSession();
        session.setAttribute("userSession",user);
        session.setAttribute("userSessionId",user.getUserId());
        logger.info("存储成功:"+info.getUsername());
        return authorizationInfo;
    }
    public void clearUserAuthByUserId(List<Integer> userIds){
        if(null == userIds || userIds.size() == 0)	return ;
        //获取所有session
        Collection<Session> sessions = redisSessionDAO.getActiveSessions();
        //定义返回
        List<SimplePrincipalCollection> list = new ArrayList<SimplePrincipalCollection>();
        for (Session session:sessions){
            //获取session登录信息。
            Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if(null != obj && obj instanceof SimplePrincipalCollection){
                //强转
                SimplePrincipalCollection spc = (SimplePrincipalCollection)obj;
                //判断用户，匹配用户ID。
                obj = spc.getPrimaryPrincipal();
                if(null != obj && obj instanceof User){
                    User user = (User) obj;
                    System.out.println("user:"+user);
                    //比较用户ID，符合即加入集合
                    if(null != user && userIds.contains(user.getUserId())){
                        list.add(spc);
                    }
                }
            }
        }
        RealmSecurityManager securityManager =
                (RealmSecurityManager) SecurityUtils.getSecurityManager();
        ShiroRealm realm = (ShiroRealm) securityManager.getRealms().iterator().next();
        for (SimplePrincipalCollection simplePrincipalCollection : list) {
            realm.clearCachedAuthorizationInfo(simplePrincipalCollection);
        }
    }
}
