package com.sylvan.hecate.common.util;

import java.time.Duration;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author sylvan
 * @date 2020/11/28
 */
public class InterestUtilsTest {
  @Test
  public void testInterestUtils() {
    Assert.assertEquals(
        2.89405943476595E-6, InterestUtils.computeInterest(0.000000000001D, 300D, 7, 52), 0.001D);
    Assert.assertEquals(
        16500.802999812895D, InterestUtils.computeTotal(0.0003, 300D, 7, 52), 0.001D);
    Assert.assertEquals(
        900.802999812895D, InterestUtils.computeInterest(0.0003, 300D, 7, 52), 0.001D);
    Assert.assertEquals(
        0.03D,
        InterestUtils.computeRate(InterestUtils.computeTotal(0.03, 300D, 7, 52), 300D, 7, 52),
        0.001D);
    Assert.assertEquals(
        0.0006D,
        InterestUtils.computeRate(InterestUtils.computeTotal(0.0006, 300D, 7, 52), 300D, 7, 52),
        0.001D);
    Assert.assertEquals(
        0.0016D,
        InterestUtils.computeRate(InterestUtils.computeTotal(0.0016, 300D, 7, 52), 300D, 7, 52),
        0.001D);
    Assert.assertEquals(
        3.1053775565537123E-4,
        InterestUtils.convertToRate(0.12, Duration.ofDays(365)),
        0.00000000001D);
  }
}
