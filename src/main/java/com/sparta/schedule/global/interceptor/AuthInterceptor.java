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
        // schedule 에서 GET 에 대한 요청은 지나가도록 하기 위해 검사
        if (HttpMethod.GET.matches(request.getMethod())) {
            return true;
        }

        HttpSession session = request.getSession(false);

        // 세션이 존재하지 않다면 로그인을 하지 않은것이므로 이용할 수 없도록 검사
        if (session == null || session.getAttribute(SessionUtil.SESSION_KEY) == null) {
            throw new AuthFailedException(ErrorCode.AUTH_FAILED);
        }

        return true;
    }
}
