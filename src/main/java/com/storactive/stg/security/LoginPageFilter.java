package com.storactive.stg.security;

import com.storactive.stg.Utils;
import com.storactive.stg.model.Interner;
import com.storactive.stg.model.User;
import com.storactive.stg.service.EmployeeService;
import com.storactive.stg.service.InternerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
class LoginPageFilter extends GenericFilterBean {

    final InternerService internerSer;
    final EmployeeService employeeSer;


    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        if (SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                && ((HttpServletRequest) request).getRequestURI().contentEquals("/login"))
            ((HttpServletResponse) response).sendRedirect("/");

        if (SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            User user = Utils.getCurrUser();

            if (user instanceof Interner)
                user = internerSer.findByIdAndCredentialsUnchanged(user);
            else
                user = employeeSer.findByIdAndCredentialsUnchanged(user);

            if (user == null)
                ((HttpServletResponse) response).sendRedirect("/logout?re-log-in");
        }

        chain.doFilter(request, response);
    }

}
