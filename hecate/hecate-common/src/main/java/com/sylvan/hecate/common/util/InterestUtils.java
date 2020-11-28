package com.sylvan.hecate.common.util;

import com.google.common.math.DoubleMath;

public class InterestUtils {

  /**
   * @param total: 最终总金额
   * @param amount: 定投金额
   * @param days : 定投周期(按天算）
   * @param times：定投次数
   * @return : 日利率
   */
  public static Double computeRate(double total, double amount, int days, int times) {
    double minRate = 1D;
    double maxRate = computeBase(days * times, total / amount);
    while (!DoubleMath.fuzzyEquals(minRate, maxRate, 0.000000000001D)) {
      double mid = (minRate + maxRate) / 2D;
      double value = ((Math.pow(mid, times + 1) - mid) / (mid - 1)) * amount;
      int compare = DoubleMath.fuzzyCompare(total, value, 0.001D);
      if (compare == 0) {
        return computeBase(days, mid);
      } else if (compare == 1) {
        minRate = mid;
      } else {
        maxRate = mid;
      }
    }
    return null;
  }

  /**
   * @param rate : 日利率
   * @param amount: 定投金额
   * @param days : 定投周期(按天算）
   * @param times：定投次数
   * @return : 最终总金额
   */
  public static double computeTotal(double rate, double amount, int days, int times) {
    if (times == 0) {
      return 0d;
    } else if (times == 1) {
      return amount * Math.pow(1D + rate, days);
    }
    return (computeTotal(rate, amount, days, times - 1) + amount) * Math.pow(1D + rate, days);
  }

  /**
   * @param rate : 日利率
   * @param amount: 定投金额
   * @param days : 定投周期(按天算）
   * @param times：定投次数
   * @return : 获得的利息
   */
  public static double computeInterest(double rate, double amount, int days, int times) {
    return computeTotal(rate, amount, days, times) - amount * times;
  }

  public static double computeBase(double exponent, double value) {
    return Math.pow(10, Math.log(value) / exponent);
  }

  public static void main(String[] args) {
    System.out.println(computeInterest(0.000000000001D, 300D, 7, 52));
    System.out.println(computeTotal(0.0003, 300D, 7, 52));
    System.out.println(computeInterest(0.0003, 300D, 7, 52));
    System.out.println(Math.pow(3, 4));
    System.out.println(Math.log10(81) / Math.log10(3));
    System.out.println(Math.pow(10, Math.log10(81) / 4));
    System.out.println(computeRate(computeTotal(0.0003, 300D, 7, 52), 300D, 7, 52));
    System.out.println(computeRate(computeTotal(0.0006, 300D, 7, 52), 300D, 7, 52));
    System.out.println(computeRate(computeTotal(0.0016, 300D, 7, 52), 300D, 7, 52));
  }
}
