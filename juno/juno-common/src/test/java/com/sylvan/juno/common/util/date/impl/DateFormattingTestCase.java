package com.sylvan.juno.common.util.date.impl;

import com.google.common.collect.Range;
import com.sylvan.juno.common.util.date.DateFormatEnum;
import com.sylvan.juno.common.util.date.DateGranularityEnum;
import com.sylvan.juno.common.util.date.TimeZoneEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author sylvan
 * @date 2020/8/10
 */
@Getter
@AllArgsConstructor
public class DateFormattingTestCase<T extends Comparable> {

  private final DateFormatEnum dateFormat;
  private final TimeZoneEnum timeZone;
  private final Range<Integer> interval;
  private final DateGranularityEnum dateGranularity;

  private final String formatResult;
  private final T parseResult;
  private final Range<T> lastDayTimeRange;
}
