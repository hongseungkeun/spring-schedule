package com.sparta.schedule.global.interceptor;

import com.sparta.schedule.global.exception.AuthFailedException;
import com.sparta.schedule.global.exception.error.ErrorCode;
import com.sparta.schedule.global.util.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HttpSession session = request.getSession(false);

        if (HttpMethod.GET.matches(request.getMethod())) {
            return true;
        }

        if (session == null || session.getAttribute(SessionUtil.SESSION_KEY) == null) {
            throw new AuthFailedException(ErrorCode.AUTH_FAILED);
        }

        return true;
    }
}
