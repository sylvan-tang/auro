package com.sylvan.hecate.common.util.date.impl;

import com.google.common.collect.Range;
import com.sylvan.hecate.common.util.date.DateFormat;
import com.sylvan.hecate.common.util.date.DateFormatEnum;
import com.sylvan.hecate.common.util.date.DateGranularityEnum;
import com.sylvan.hecate.common.util.date.TimeZoneEnum;

import org.joda.time.DateTime;

/**
 * @author sylvan
 * @date 2020/8/8
 */
public class TimestampDateFormatter implements DateFormat<Long> {

    @Override
    public String formatFromT(
            Long object, DateFormatEnum dateFormatEnum, TimeZoneEnum timeZoneEnum) {
        DateTime dateTime = new DateTime(object, timeZoneEnum.getDateTimeZone());
        return (dateTime.toString(dateFormatEnum.getDateTimeFormatter()));
    }

    @Override
    public Long parseToT(String object, DateFormatEnum dateFormatEnum, TimeZoneEnum timeZoneEnum) {
        DateTime dateTime =
                dateFormatEnum
                        .getDateTimeFormatter()
                        .parseDateTime(object)
                        .toDateTime(timeZoneEnum.getDateTimeZone());
        return dateTime.getMillis();
    }

    @Override
    public Range<Long> getTimeRange(
            Long object,
            Range<Integer> interval,
            DateGranularityEnum dateGranularity,
            TimeZoneEnum timeZoneEnum) {
        // start of the day
        long start =
                parseToT(
                        formatFromT(object, dateGranularity.getDateFormatEnum(), timeZoneEnum),
                        dateGranularity.getDateFormatEnum(),
                        timeZoneEnum);
        long end = start + dateGranularity.getDuration().toMillis() - 1;
        start += interval.lowerEndpoint() * dateGranularity.getDuration().toMillis();
        end += interval.upperEndpoint() * dateGranularity.getDuration().toMillis();
        return Range.closed(start, end);
    }
}
