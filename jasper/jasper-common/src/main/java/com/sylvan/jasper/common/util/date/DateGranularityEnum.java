package com.sylvan.jasper.common.util.date;

import java.time.Duration;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author sylvan
 * @date 2020/8/11
 */
@AllArgsConstructor
@Getter
public enum DateGranularityEnum {
  /** 一天的时间粒度 */
  ONE_DAY(Duration.ofDays(1), DateFormatEnum.DAY_OF_YEAR_IN_COMPACT),
  /** 一周的时间粒度 */
  ONE_WEEK(Duration.ofDays(7), DateFormatEnum.WEEK_OF_YEAR_IN_COMPACT),
  /** 一毫秒的时间粒度 */
  ONE_MILLIS(Duration.ofMillis(1), DateFormatEnum.DATE_WITH_TIME_ZONE);

  private final Duration duration;
  private final DateFormatEnum dateFormatEnum;
}
