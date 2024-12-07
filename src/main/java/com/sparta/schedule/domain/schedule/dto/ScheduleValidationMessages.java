package com.sparta.schedule.domain.schedule.dto;

public class ScheduleValidationMessages {
    public static final String UPDATED_PATTERN_REG = "\\d{4}-\\d{2}-\\d{2}";
    public static final String UPDATED_PATTERN_MESSAGE = "수정일 형식이 올바르지 않습니다.";
    public static final String TITLE_BLANK_MESSAGE = "제목을 입력해주세요.";
    public static final int TODO_MAX = 200;
    public static final String TODO_MAX_MESSAGE = "글은 200자 이상 적을 수 없습니다.";
    public static final String TODO_BLANK_MESSAGE = "글을 입력해주세요.";
}