package com.sylvan.juno.common.util.date.impl;

import com.google.common.collect.Range;
import com.sylvan.juno.common.util.date.DateConstant;
import com.sylvan.juno.common.util.date.DateFormatEnum;
import com.sylvan.juno.common.util.date.DateGranularityEnum;
import com.sylvan.juno.common.util.date.TimeZoneEnum;
import java.util.Arrays;
import org.junit.Test;

/**
 * @author sylvan
 * @date 2020/8/8
 */
public class TimestampDateFormatEnumTest {

  @Test
  public void testFormatterNormal() {
    new DateFormattingBaseTest<>(
            DateConstant.TIMESTAMP_DATE_FORMATTER,
            1596988573000L, // 北京时间 2020-08-09 23:56:13
            Arrays.asList(
                new DateFormattingTestCase<>(
                    DateFormatEnum.DAY_OF_YEAR_IN_COMPACT,
                    TimeZoneEnum.SHANGHAI,
                    Range.closed(-6, 0),
                    DateGranularityEnum.ONE_DAY,
                    "20200809",
                    1596902400000L, // 北京时间 2020-08-09 00:00:00
                    /*
                     * 北京时间 [2020-08-03 00:00:00, 2020-08-09 23:59:59:9999]
                     */
                    Range.closed(1596384000000L, 1596988799999L)),
                new DateFormattingTestCase<>(
                    DateFormatEnum.DAY_OF_YEAR_WITH_CROSSBAR,
                    TimeZoneEnum.SHANGHAI,
                    Range.closed(-6, 0),
                    DateGranularityEnum.ONE_DAY,
                    "2020-08-09",
                    1596902400000L,
                    /*
                     * 北京时间 [2020-08-03 00:00:00, 2020-08-09 23:59:59:9999]
                     */
                    Range.closed(1596384000000L, 1596988799999L)),
                new DateFormattingTestCase<>(
                    DateFormatEnum.WEEK_OF_YEAR_IN_COMPACT,
                    TimeZoneEnum.SHANGHAI,
                    Range.closed(0, 0),
                    DateGranularityEnum.ONE_WEEK,
                    "202032",
                    1596384000000L,
                    /*
                     * 北京时间 [2020-08-03 00:00:00, 2020-08-09 23:59:59:9999]
                     */
                    Range.closed(1596384000000L, 1596988799999L))))
        .runTest();
  }

  @Test
  public void testFormatterWithWeek() {
    new DateFormattingBaseTest<>(
            DateConstant.TIMESTAMP_DATE_FORMATTER,
            1596998573000L, // 北京2020-08-10 02:42:53
            Arrays.asList(
                new DateFormattingTestCase<>(
                    DateFormatEnum.DAY_OF_YEAR_IN_COMPACT,
                    TimeZoneEnum.SHANGHAI,
                    Range.closed(-6, 0),
                    DateGranularityEnum.ONE_DAY,
                    "20200810",
                    1596988800000L,
                    /*
                     * 北京时间 [2020-08-04 00:00:00, 2020-08-10 23:59:59:9999]
                     */
                    Range.closed(1596470400000L, 1597075199999L)),
                new DateFormattingTestCase<>(
                    DateFormatEnum.WEEK_OF_YEAR_IN_COMPACT,
                    TimeZoneEnum.SHANGHAI,
                    Range.closed(0, 0),
                    DateGranularityEnum.ONE_WEEK,
                    "202033",
                    1596988800000L,
                    /*
                     * 北京时间 [2020-08-10 00:00:00, 2020-08-16 23:59:59:9999]
                     */
                    Range.closed(1596988800000L, 1597593599999L))))
        .runTest();
  }

  @Test
  public void testMinusWeek() {
    new DateFormattingBaseTest<>(
            DateConstant.TIMESTAMP_DATE_FORMATTER,
            1596998573000L, // 北京2020-08-10 02:42:53
            Arrays.asList(
                new DateFormattingTestCase<>(
                    DateFormatEnum.WEEK_OF_YEAR_IN_COMPACT,
                    TimeZoneEnum.SHANGHAI,
                    Range.closed(-1, -1),
                    DateGranularityEnum.ONE_WEEK,
                    "202033",
                    1596988800000L,
                    /*
                     * 北京时间 [2020-08-03 00:00:00, 2020-08-09 23:59:59]
                     */
                    Range.closed(1596384000000L, 1596988799999L))))
        .runTest();
  }

  @Test
  public void testMinusDay() {
    new DateFormattingBaseTest<>(
            DateConstant.TIMESTAMP_DATE_FORMATTER,
            1596798573000L, // 2020-08-07 19:09:33
            Arrays.asList(
                new DateFormattingTestCase<>(
                    DateFormatEnum.DAY_OF_YEAR_IN_COMPACT,
                    TimeZoneEnum.SHANGHAI,
                    Range.closed(-6, 0),
                    DateGranularityEnum.ONE_DAY,
                    "20200807",
                    1596729600000L, // 2020-08-07 00:00:00
                    /*
                     * 北京时间 [2020-08-01 00:00:00, 2020-08-07 23:59:59]
                     */
                    Range.closed(1596211200000L, 1596815999999L))))
        .runTest();
  }

  @Test
  public void testWithUtcTime() {
    new DateFormattingBaseTest<>(
            DateConstant.TIMESTAMP_DATE_FORMATTER,
            1596998573000L, // 北京2020-08-10 02:42:53
            Arrays.asList(
                new DateFormattingTestCase<>(
                    DateFormatEnum.DAY_OF_YEAR_IN_COMPACT,
                    TimeZoneEnum.UTC,
                    Range.closed(-6, 0),
                    DateGranularityEnum.ONE_DAY,
                    "20200809",
                    1596902400000L, // 2020-08-09 08:00:00
                    /*
                     * 北京时间 [2020-08-01 08:00:00, 2020-08-08 07:59:59]
                     */
                    Range.closed(1596384000000L, 1596988799999L))))
        .runTest();
  }

  @Test
  public void testDateFormatForConvertToString() {
    new DateFormattingBaseTest<>(
            DateConstant.TIMESTAMP_DATE_FORMATTER,
            1596998573000L, // 北京2020-08-10 02:42:53
            Arrays.asList(
                new DateFormattingTestCase<>(
                    DateFormatEnum.DATE_WITH_TIME_ZONE,
                    TimeZoneEnum.UTC,
                    Range.closed(-6, 0),
                    DateGranularityEnum.ONE_MILLIS,
                    "2020-08-09 18:42:53.000 +0000",
                    1596998573000L, // 北京2020-08-10 02:42:53.000
                    /*
                     * 北京时间 [2020-08-10 02:42:52.994, 2020-08-10 02:42:53.000]
                     */
                    Range.closed(1596998572994L, 1596998573000L))))
        .runTest();
    new DateFormattingBaseTest<>(
            DateConstant.TIMESTAMP_DATE_FORMATTER,
            1596998573000L, // 北京2020-08-10 02:42:53
            Arrays.asList(
                new DateFormattingTestCase<>(
                    DateFormatEnum.DATE_WITH_TIME_ZONE,
                    TimeZoneEnum.SHANGHAI,
                    Range.closed(-6, 0),
                    DateGranularityEnum.ONE_MILLIS,
                    "2020-08-10 02:42:53.000 +0800",
                    1596998573000L, // 北京2020-08-10 02:42:53.000
                    /*
                     * 北京时间 [2020-08-10 02:42:52.994, 2020-08-10 02:42:53.000]
                     */
                    Range.closed(1596998572994L, 1596998573000L))))
        .runTest();
  }
}
