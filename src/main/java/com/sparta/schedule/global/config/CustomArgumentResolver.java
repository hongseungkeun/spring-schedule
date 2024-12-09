package com.sparta.schedule.global.config;

import com.sparta.schedule.global.annotation.LoginId;
import com.sparta.schedule.global.util.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class CustomArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // loginId 어노테이션을 찾아
        // resolveArgument 메소드에서 session 의 userId를 주입
        return parameter.hasParameterAnnotation(LoginId.class);
    }

    @Override
    public Long resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();

        return SessionUtil.getSession(httpServletRequest.getSession());
    }
}