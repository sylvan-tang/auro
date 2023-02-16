package com.sylvan.auro.common.util.date;

import lombok.Getter;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * @author sylvan
 * @date 2020/8/8
 */
@Getter
public enum DateFormatEnum {
  /** 紧凑的年月日格式 */
  DAY_OF_YEAR_IN_COMPACT("yyyyMMdd"),

  /** 使用横杆作为年月日连接符 */
  DAY_OF_YEAR_WITH_CROSSBAR("yyyy-MM-dd"),

  /** 获得年份和周数的紧凑格式 */
  WEEK_OF_YEAR_IN_COMPACT("yyyyw"),

  /** 精确到毫秒的时间，带着时区 */
  DATE_WITH_TIME_ZONE("yyyy-MM-dd HH:mm:ss.SSS Z");

  /** 时间格式 pattern */
  private final DateTimeFormatter dateTimeFormatter;

  DateFormatEnum(String dateFormatString) {
    this.dateTimeFormatter = DateTimeFormat.forPattern(dateFormatString);
  }
}
