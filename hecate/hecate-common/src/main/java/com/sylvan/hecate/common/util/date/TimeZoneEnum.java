package com.sylvan.hecate.common.util.date;

import lombok.Getter;

import org.joda.time.DateTimeZone;

import java.util.Calendar;

/**
 * @author sylvan
 * @date 2020/8/10
 */
@Getter
public enum TimeZoneEnum {
    /** 上海作为默认时区 */
    SHANGHAI("Asia/Shanghai", Calendar.MONDAY),
    UTC("UTC", Calendar.SUNDAY);

    private final int firstDayOfWeek;
    private final DateTimeZone dateTimeZone;

    TimeZoneEnum(String timezone, int firstDayOfWeek) {
        this.firstDayOfWeek = firstDayOfWeek;
        this.dateTimeZone = DateTimeZone.forID(timezone);
    }
}
