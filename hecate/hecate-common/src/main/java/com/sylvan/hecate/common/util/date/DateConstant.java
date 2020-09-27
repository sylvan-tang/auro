package com.sylvan.hecate.common.util.date;

import com.sylvan.hecate.common.util.date.impl.TimestampDateFormatter;

/**
 * @author sylvan
 * @date 2020/8/8
 */
public class DateConstant {

  /** 对系统时间戳进行格式化 */
  public static final TimestampDateFormatter TIMESTAMP_DATE_FORMATTER =
      new TimestampDateFormatter();
}
