package com.sparta.schedule.global.config;

import com.sparta.schedule.global.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 로그아웃, schedule 경로는 로그인이 필요하므로 인터셉터를 검사하도록 설정
        registry.addInterceptor(new AuthInterceptor())
                .addPathPatterns("/api/schedules/**")
                .addPathPatterns("/api/users/logout");
    }

    // loginId 어노테이션에 userId를 주입해주기 위한 메소드
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new CustomArgumentResolver());
    }
}