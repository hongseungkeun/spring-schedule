package com.sparta.schedule.api.user;

import com.sparta.schedule.domain.user.dto.request.UserLoginReq;
import com.sparta.schedule.domain.user.dto.request.UserSignUpReq;
import com.sparta.schedule.domain.user.service.UserService;
import com.sparta.schedule.global.util.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody @Valid final UserSignUpReq request) {
        userService.signUp(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody @Valid final UserLoginReq request, HttpServletRequest httpServletRequest) {
        Long id = userService.login(request);
        SessionUtil.createSession(id, httpServletRequest);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logoutUser(HttpSession session) {
        SessionUtil.removeSession(session);

        return ResponseEntity.ok().build();
    }
}