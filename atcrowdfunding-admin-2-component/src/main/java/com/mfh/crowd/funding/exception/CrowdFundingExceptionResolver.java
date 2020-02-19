package com.mfh.crowd.funding.exception;

import com.google.gson.Gson;
import com.mfh.crowd.funding.constants.CrowdFundingConstant;
import com.mfh.crowd.funding.entity.ResultEntity;
import com.mfh.crowd.funding.util.CrowdFundingUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author mfh
 * @date 2020/1/31 15:55
 */
@ControllerAdvice
public class CrowdFundingExceptionResolver {
    @ExceptionHandler(value = Exception.class)
    public ModelAndView catchException(Exception ex, HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean isAsyncRequest = CrowdFundingUtils.checkAsyncRequest(request);
        if (isAsyncRequest) {
            String exClassName = ex.getClass().getName();
            String message = CrowdFundingConstant.EXCEPTION_MESSAGE_MAP.get(exClassName);
            if (!CrowdFundingUtils.stringEffective(message)) {
                message = "系统发生未知错误，请联系管理员！";
            }
            ResultEntity<String> failure = ResultEntity.failed(ResultEntity.NO_DATA, message);
            PrintWriter writer = response.getWriter();
            Gson gson = new Gson();
            String json = gson.toJson(failure);
            writer.write(json);
            writer.flush();
            writer.close();
            return null;
        }
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex);
        mav.setViewName("system-error");
        return mav;
    }
}
