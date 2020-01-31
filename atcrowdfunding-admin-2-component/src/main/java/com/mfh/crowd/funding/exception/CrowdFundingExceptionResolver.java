package com.mfh.crowd.funding.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author mfh
 * @date 2020/1/31 15:55
 */
@ControllerAdvice
public class CrowdFundingExceptionResolver {
    @ExceptionHandler(value = Exception.class)
    public ModelAndView catchException(Exception ex) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex);
        mav.setViewName("system-error");
        return mav;
    }
}
