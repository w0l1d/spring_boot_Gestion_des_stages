package com.storactive.stg.config;

import com.storactive.stg.Utils;
import com.storactive.stg.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class AlertInterceptor implements HandlerInterceptor {

    final AlertService alertSer;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (Utils.isAuthenticated()) {
            if (request.isUserInRole("ROLE_ADMIN")) {
                request.setAttribute("alerts", alertSer.getIndex());
                request.setAttribute("unsolvedCount", alertSer.countByStatus((short) 0));
            } else {
                request.setAttribute("alerts", alertSer.getIndexInterner());
                request.setAttribute("unsolvedCount",
                        alertSer.countByStatusForInterner((short) 0));
            }

        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

}