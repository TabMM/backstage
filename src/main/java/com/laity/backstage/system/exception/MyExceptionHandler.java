package com.laity.backstage.system.exception;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.alibaba.fastjson.support.spring.annotation.FastJsonView;
import com.laity.backstage.system.entity.vo.ResultVO;
import com.laity.backstage.system.utils.ModelAndViewUtil;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author D.F Douglas
 * @version 1.0.0
 * @ClassName MyExceptionHandler
 * @Description 全局异常处理
 * @createTime 2019/6/8/21:16
 */
public class MyExceptionHandler implements HandlerExceptionResolver {
    /*
     * @Author  D.F Douglas
     * @Description //重写父类方法
     * @Date 22:02 2019/6/8
     * @Param [request, response, o, e]
     * @return com.laity.backstage.system.utils.ModelAndViewUtil
     **/
    @Override
    public ModelAndViewUtil resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {
        ModelAndViewUtil modelAndView =new ModelAndViewUtil();
        ResultVO resultVO =new ResultVO();
        if (e instanceof UnauthenticatedException){
            resultVO.setCode("10001");
            resultVO.setMsg("token错误");
        }else if (e instanceof UnauthorizedException){
            resultVO.setCode("10002");
            resultVO.setMsg("用户无权限");
        }else {
            resultVO.setCode("10003");
            resultVO.setMsg(e.getMessage());
        }
         modelAndView.setView(resultVO);
        return modelAndView;
    }
}
