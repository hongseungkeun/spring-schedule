package com.sparta.schedule.api.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.schedule.domain.user.dto.request.UserLoginReq;
import com.sparta.schedule.domain.user.dto.request.UserSignUpReq;
import com.sparta.schedule.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
public class UserControllerTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("유저는 회원가입 할 수 있다")
    void user_signUp() throws Exception {
        UserSignUpReq request = new UserSignUpReq("abc@naver.com", "12345678", "테스트");

        mockMvc.perform(post("/api/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("user-signUp",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").description("유저 이메일")
                                        .attributes(Attributes.key("constraint").value("이메일 형식")),
                                fieldWithPath("password").description("유저 패스워드")
                                        .attributes(Attributes.key("constraint").value("최소 8자")),
                                fieldWithPath("name").description("유저 이름").optional()
                        )
                ));
    }

    @Test
    @DisplayName("유저는 로그인 할 수 있다")
    void user_login() throws Exception {
        UserSignUpReq request = new UserSignUpReq("abc@naver.com", "12345678", "테스트");
        userRepository.save(request);

        UserLoginReq loginReq = new UserLoginReq("abc@naver.com", "12345678");

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginReq)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user-login",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").description("유저 이메일")
                                        .attributes(Attributes.key("constraint").value("이메일 형식")),
                                fieldWithPath("password").description("유저 패스워드")
                                        .attributes(Attributes.key("constraint").value("최소 8자"))
                        )
                ));
    }
}