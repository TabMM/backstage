package com.laity.backstage.system.controller;

import com.laity.backstage.system.entity.User;
import com.laity.backstage.system.entity.vo.ResultVO;
import com.laity.backstage.system.service.SysUserService;
import com.laity.backstage.system.utils.PasswordUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author D.F Douglas
 * @version 1.0.0
 * @ClassName LoginController
 * @Description TODO
 * @createTime 2019/6/4/17:46
 */
@Api(value = "登录相关接口")
@Controller
public class UserController {
    @Autowired
    SysUserService userService;
    @ApiOperation("登录")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ResultVO login(User user) {
        ResultVO resultVO = new ResultVO();
        if (StringUtils.isEmpty(user.getUsername())||StringUtils.isEmpty(user.getPassword())){
            resultVO.setMsg("用户名密码不能为空！");
        }else {
            Subject subject =SecurityUtils.getSubject();
            UsernamePasswordToken token =new UsernamePasswordToken(user.getUsername(),user.getPassword());
            try {
                subject.login(token);
                //return "redirect:usersPage";
                resultVO.setHtml("usersPage.html");
            } catch (LockedAccountException e) {
                token.clear();
                resultVO.setMsg("用户已经被锁定不能登录，请联系管理员！");
                resultVO.setHtml("login.html");
            }catch (AuthenticationException e){
                token.clear();
                resultVO.setMsg("用户名或密码不正确！");
                resultVO.setHtml("login.html");
            }
        }
        return resultVO;
    }
    @ApiOperation("注册")
    @RequestMapping(value = "/ajaxRegister",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ResultVO register(@RequestBody User user){
        System.out.println(user.getPassword());
        System.out.println(user.getUsername());
        ResultVO resultVO =new ResultVO();
        User u=userService.selectByUsername(user);
        if (u!=null){
            resultVO.setCode("402");
            resultVO.setMsg("用户已开户！");
        }else {
            try {
                user.setStatus((byte)1);
                userService.register(PasswordUtils.encryptPassword(user));
                resultVO.setCode("200");
                resultVO.setMsg("开户成功！");
                resultVO.setHtml("login.html");
            } catch (Exception e) {
                e.printStackTrace();
                resultVO.setCode("404");
                resultVO.setMsg("开户失败！");
            }
        }
        return resultVO;
    }
    @ApiOperation(value = "ajax登录")
    @RequestMapping(value = "/ajaxLogin",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ResultVO ajaxLogin(@RequestBody User user){
        System.out.println(user.getPassword());
        System.out.println(user.getUsername());
        ResultVO resultVO =new ResultVO();
        Subject subject =SecurityUtils.getSubject();
        UsernamePasswordToken token =new UsernamePasswordToken(user.getUsername(),user.getPassword());
        try {
            subject.login(token);
            resultVO.setToken(subject.getSession().getId());
            resultVO.setCode("200");
            resultVO.setMsg("登录成功");
        }catch (IncorrectCredentialsException e){
            resultVO.setCode("401");
            resultVO.setMsg("密码错误");
        }catch (LockedAccountException e){
            resultVO.setCode("402");
            resultVO.setMsg("登录失败，该用户已被冻结");
        }catch (AuthenticationException e) {
            resultVO.setCode("404");
            resultVO.setMsg("该用户不存在");
        }

        return resultVO;
    }
    @RequestMapping(value = "/unauth")
    @ResponseBody
    public ResultVO unauth(){
        ResultVO resultVO =new ResultVO();
        resultVO.setCode("405");
        resultVO.setMsg("未登录");
        return resultVO;
    }
    @ApiOperation(value = "ajax获取用户列表")
    @RequestMapping(value = "/getList",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ResultVO getlist(){
        ResultVO resultVO =new ResultVO();
        List<User> userList=userService.getUserList();
        resultVO.setList(userList);
        return  resultVO;

    }
    @ApiOperation(value = "ajax禁用用户")
    @RequestMapping(value = "/delUser",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ResultVO updateByUserId(@RequestBody User user){
        ResultVO resultVO =new ResultVO();
        try {
            userService.updateByUserId(user);
            resultVO.setCode("ok");
        } catch (Exception e) {
            resultVO.setCode("error");
            e.printStackTrace();
        }
        return resultVO;
    }
    @ApiOperation(value = "ajax启用用户")
    @RequestMapping(value = "/openUser",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ResultVO updateByUsername(@RequestBody User user){
        ResultVO resultVO =new ResultVO();
        try {
            userService.updateByUsername(user);
            resultVO.setCode("ok");
        } catch (Exception e) {
            resultVO.setCode("error");
            e.printStackTrace();
        }
        return resultVO;
    }
}
