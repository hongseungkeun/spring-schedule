package com.sparta.schedule.api.schedule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.schedule.domain.schedule.dto.request.ScheduleCreateReq;
import com.sparta.schedule.domain.schedule.dto.request.ScheduleUpdateReq;
import com.sparta.schedule.domain.schedule.repository.ScheduleRepository;
import com.sparta.schedule.domain.user.dto.request.UserSignUpReq;
import com.sparta.schedule.domain.user.entity.User;
import com.sparta.schedule.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
public class ScheduleControllerTest {
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        UserSignUpReq userRequest = new UserSignUpReq("abc5@naver.com", "12345678", "테스트");
        userRepository.save(userRequest);

        User dummyUser = getUser();

        List<ScheduleCreateReq> requests = List.of(
                new ScheduleCreateReq("제목 테스트1", "1테스트입니다."),
                new ScheduleCreateReq("제목 테스트2", "2테스트입니다."),
                new ScheduleCreateReq("제목 테스트3", "3테스트입니다."),
                new ScheduleCreateReq("제목 테스트4", "4테스트입니다."),
                new ScheduleCreateReq("제목 테스트5", "5테스트입니다."),
                new ScheduleCreateReq("제목 테스트6", "6테스트입니다."),
                new ScheduleCreateReq("제목 테스트7", "7테스트입니다."),
                new ScheduleCreateReq("제목 테스트8", "8테스트입니다."),
                new ScheduleCreateReq("제목 테스트9", "9테스트입니다."),
                new ScheduleCreateReq("제목 테스트10", "10테스트입니다.")
        );

        for (ScheduleCreateReq request : requests) {
            scheduleRepository.save(request, dummyUser.getUserId());
        }
    }

    @DisplayName("유저는 일정을 추가할 수 있다.")
    @Test
    void create_schedule() throws Exception {
        User dummyUser = getUser();
        ScheduleCreateReq request = new ScheduleCreateReq("제목 테스트", "테스트입니다.");

        MockHttpSession dummySession = createDummySession(dummyUser.getUserId());

        mockMvc.perform(post("/api/schedules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .session(dummySession))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("schedule-create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("title").description("일정 제목"),
                                fieldWithPath("todo").description("일정 할일")
                                        .attributes(Attributes.key("constraint").value("최대 200자"))
                        )
                ));
    }

    @DisplayName("일정을 전체 페이징 조회할 수 있다.")
    @Test
    void read_schedules() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("page", "0");
        params.add("size", "3");

        mockMvc.perform(get("/api/schedules")
                        .queryParams(params))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("schedule-read-all",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        queryParameters(
                                parameterWithName("page").description("페이지"),
                                parameterWithName("size").description("페이지 사이즈")
                        ),
                        responseFields(
                                fieldWithPath("[].scheduleId").description("일정 번호"),
                                fieldWithPath("[].title").description("일정 제목"),
                                fieldWithPath("[].todo").description("일정 할일"),
                                fieldWithPath("[].name").description("작성자명"),
                                fieldWithPath("[].createdAt").description("일정 작성일"),
                                fieldWithPath("[].updatedAt").description("일정 수정일"),
                                fieldWithPath("[].userId").description("작성자")
                        )
                ));
    }

    @DisplayName("일정을 전체 필터 검색 조회할 수 있다.")
    @Test
    void read_schedules_by_id_and_updatedAt() throws Exception {
        User dummyUser = getUser();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", String.valueOf(dummyUser.getUserId()));
        params.add("updatedAt", "2024-12-09");
        params.add("page", "0");
        params.add("size", "3");

        mockMvc.perform(get("/api/schedules")
                        .queryParams(params))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("schedule-read-all-by-id-and-updated-at",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        queryParameters(
                                parameterWithName("id").description("유저 아이디"),
                                parameterWithName("updatedAt").description("일정 수정일"),
                                parameterWithName("page").description("페이지"),
                                parameterWithName("size").description("페이지 사이즈")
                        ),
                        responseFields(
                                fieldWithPath("[].scheduleId").description("일정 번호"),
                                fieldWithPath("[].title").description("일정 제목"),
                                fieldWithPath("[].todo").description("일정 할일"),
                                fieldWithPath("[].name").description("작성자명"),
                                fieldWithPath("[].createdAt").description("일정 작성일"),
                                fieldWithPath("[].updatedAt").description("일정 수정일"),
                                fieldWithPath("[].userId").description("작성자")
                        )
                ));
    }

    @DisplayName("일정을 단건 조회할 수 있다.")
    @Test
    void read_schedule() throws Exception {
        Long scheduleId = getScheduleId(getUser());

        mockMvc.perform(get("/api/schedules/{scheduleId}", scheduleId))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("schedule-read",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("scheduleId").description("조회할 일정 번호")
                        ),
                        responseFields(
                                fieldWithPath("scheduleId").description("일정 번호"),
                                fieldWithPath("title").description("일정 제목"),
                                fieldWithPath("todo").description("일정 할일"),
                                fieldWithPath("name").description("작성자명"),
                                fieldWithPath("createdAt").description("일정 작성일"),
                                fieldWithPath("updatedAt").description("일정 수정일"),
                                fieldWithPath("userId").description("작성자")
                        )
                ));
    }

    @DisplayName("유저는 일정을 수정할 수 있다.")
    @Test
    void update_schedule() throws Exception {
        User dummyUser = getUser();
        Long scheduleId = getScheduleId(dummyUser);

        MockHttpSession dummySession = createDummySession(dummyUser.getUserId());

        ScheduleUpdateReq request = new ScheduleUpdateReq("테스트 변경");

        mockMvc.perform(patch("/api/schedules/{scheduleId}", scheduleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .session(dummySession))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("schedule-update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("scheduleId").description("조회할 일정 번호")
                        ),
                        requestFields(
                                fieldWithPath("todo").description("일정 할일")
                                        .attributes(Attributes.key("constraint").value("최대 200자"))
                        )
                ));
    }

    @DisplayName("유저는 일정을 삭제할 수 있다.")
    @Test
    void delete_schedule() throws Exception {
        User dummyUser = getUser();
        Long scheduleId = getScheduleId(dummyUser);
        MockHttpSession dummySession = createDummySession(dummyUser.getUserId());

        mockMvc.perform(delete("/api/schedules/{scheduleId}", scheduleId)
                        .session(dummySession))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("schedule-delete",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("scheduleId").description("조회할 일정 번호")
                        )
                ));
    }

    private User getUser() {
        return userRepository.findByEmail("abc5@naver.com").get();
    }

    private Long getScheduleId(User user) {
        ScheduleCreateReq request = new ScheduleCreateReq("제목 테스트12", "12테스트입니다.");

        return scheduleRepository.save(request, user.getUserId());
    }

    private MockHttpSession createDummySession(Long id) {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("SESSION-KEY", id);
        return session;
    }
}