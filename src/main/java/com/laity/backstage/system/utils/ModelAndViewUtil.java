package com.laity.backstage.system.utils;

import com.laity.backstage.system.entity.vo.ResultVO;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

/**
 * @author D.F Douglas
 * @version 1.0.0
 * @ClassName ModeAndViewUtil
 * @Description TODO
 * @createTime 2019/6/8/21:43
 */
public class ModelAndViewUtil extends ModelAndView {
    @Nullable
    private Object view;
    public void setView(Object view) {
        this.view =view;
    }
}
