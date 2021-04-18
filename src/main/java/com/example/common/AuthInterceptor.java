package com.example.common;

import com.example.entity.Permission;
import com.example.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 拦截器
 */
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String servletPath = request.getServletPath();
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("/page/end/login.html");
            return false;
        }
//        List<Permission> permission = user.getPermission();
//        if (permission.stream().noneMatch(p -> servletPath.contains(p.getFlag())) && !servletPath.contains("index")) {
//            response.sendRedirect("/page/end/auth.html");
//            return false;
//        }
        return true;
    }

}
