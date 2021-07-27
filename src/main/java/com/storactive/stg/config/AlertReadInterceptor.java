package com.storactive.stg.config;

import com.storactive.stg.Utils;
import com.storactive.stg.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@RequiredArgsConstructor
public class AlertReadInterceptor implements HandlerInterceptor {

    final AlertService alertSer;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler)
            throws Exception {
        if (Utils.isAuthenticated() && !Objects.isNull(request.getParameter("a"))) {
            int alertId;
            try {
                alertId = Integer.parseInt(request.getParameter("a"));
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Alert ID not Correct");
                return HandlerInterceptor.super.preHandle(request, response, handler);
            }
            System.out.println("/***** hello ");
            System.out.println(alertId);
            alertSer.markAsRead(alertId);
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
