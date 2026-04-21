package com.ucs.contactmessage.service.helper;

import com.ucs.exception.ConflictException;
import com.ucs.exception.ErrorMessageType;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

@Component
public class DateHelper {
    public LocalDateTime[] getRange(String beginDateString, String endDateString) {
        try {
            LocalDateTime start = LocalDate.parse(beginDateString).atStartOfDay();
            LocalDateTime end = LocalDate.parse(endDateString).atTime(LocalTime.MAX);
            return new LocalDateTime[]{start, end};
        } catch (DateTimeParseException e) {
            throw new ConflictException(ErrorMessageType.WRONG_DATE_FORMAT, beginDateString);
        }
    }
}
