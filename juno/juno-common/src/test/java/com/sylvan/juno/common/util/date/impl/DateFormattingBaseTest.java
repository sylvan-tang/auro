package com.sylvan.juno.common.util.date.impl;

import com.sylvan.juno.common.util.date.DateFormat;
import java.util.List;
import org.junit.Assert;

/**
 * @author sylvan
 * @date 2020/8/8
 */
public class DateFormattingBaseTest<T extends Comparable> {

  private final DateFormat<T> tDateFormat;
  private final List<DateFormattingTestCase<T>> testCaseList;

  private final T object;

  protected DateFormattingBaseTest(
      DateFormat<T> tDateFormat, T object, List<DateFormattingTestCase<T>> testCaseList) {
    this.tDateFormat = tDateFormat;
    this.testCaseList = testCaseList;
    this.object = object;
  }

  private void testOneCase(DateFormattingTestCase<T> testCase) {
    String formatResult =
        tDateFormat.formatFromT(object, testCase.getDateFormat(), testCase.getTimeZone());
    Assert.assertEquals(formatResult, testCase.getFormatResult());

    T parseResult =
        tDateFormat.parseToT(formatResult, testCase.getDateFormat(), testCase.getTimeZone());
    Assert.assertEquals(parseResult, testCase.getParseResult());

    Assert.assertEquals(
        tDateFormat.getTimeRange(
            object, testCase.getInterval(), testCase.getDateGranularity(), testCase.getTimeZone()),
        testCase.getLastDayTimeRange());
  }

  protected void runTest() {
    testCaseList.forEach(this::testOneCase);
  }
}
