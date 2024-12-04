package com.sparta.schedule.global.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtil {
    public static LocalDate convertToLocalDate(Date date) {
        return date.toLocalDate();
    }
}