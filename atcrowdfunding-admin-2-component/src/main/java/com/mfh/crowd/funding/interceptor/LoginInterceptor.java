package com.mfh.crowd.funding.interceptor;

import com.google.gson.Gson;
import com.mfh.crowd.funding.constants.CrowdFundingConstant;
import com.mfh.crowd.funding.entity.Admin;
import com.mfh.crowd.funding.entity.ResultEntity;
import com.mfh.crowd.funding.util.CrowdFundingUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * @author mfh
 * @date 2020/1/31 13:41
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Admin admin = (Admin)session.getAttribute(CrowdFundingConstant.ATTR_NAME_LOGIN_ADMIN);
        if (null == admin) {
            boolean isAsyncRequest = CrowdFundingUtils.checkAsyncRequest(request);
            if (isAsyncRequest) {
                ResultEntity<String> failure = ResultEntity.failed(ResultEntity.NO_DATA, CrowdFundingConstant.MESSAGE_ACCESS_DENIED);
                Gson gson = new Gson();
                String json = gson.toJson(failure);
                PrintWriter writer = response.getWriter();
                writer.write(json);
                writer.flush();
                writer.close();
                return false;
            }

            // 将提示消息存入 request 域
            request.setAttribute(CrowdFundingConstant.ATTR_NAME_MESSAGE, CrowdFundingConstant.MESSAGE_ACCESS_DENIED);
            // 转发至登录页面
            request.getRequestDispatcher("/WEB-INF/admin-login.jsp").forward(request, response);
            return false;
        }
        // admin 有效，则放行继续执行后面的操作
        return true;
    }
}
