package com.sparta.schedule.global.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionUtil {

    public static final String SESSION_KEY = "SESSION-KEY";

    public static void createSession(Long id, HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();

        if (hasKeyAttribute(session)) {
            removeSession(session);
        }
        createSession(id, httpServletRequest.getSession());
    }

    public static Long getSession(HttpSession session) {
        return (Long) session.getAttribute(SESSION_KEY);
    }

    public static void removeSession(HttpSession session) {
        session.invalidate();
    }

    private static boolean hasKeyAttribute(HttpSession session) {
        return session.getAttribute(SESSION_KEY) != null;
    }

    private static void createSession(Long id, HttpSession session) {
        session.setAttribute(SESSION_KEY, id);
    }
}