package com.sylvan.hecate.common.util.date;

import com.google.common.collect.Range;

/**
 * @author sylvan
 * @date 2020/8/8
 */
public interface DateFormat<T extends Comparable> {

    /** 根据指定时区将时间转换为字符串 */
    String formatFromT(T object, DateFormatEnum dateFormatEnum, TimeZoneEnum timeZoneEnum);

    /** 根据指定时区将字符串转换为时间 */
    T parseToT(String object, DateFormatEnum dateFormatEnum, TimeZoneEnum timeZoneEnum);

    /**
     * 根据时间位移获得一个时间的闭合区间
     *
     * @param object: 指定时间
     * @param interval: 位移步数区间, 与时间粒度结合获得时间区间
     * @param dateGranularity: 时间粒度
     * @return : [开始时间戳(毫秒)，结束时间戳(毫秒)]
     */
    Range<T> getTimeRange(
            T object,
            Range<Integer> interval,
            DateGranularityEnum dateGranularity,
            TimeZoneEnum timeZoneEnum);
}
