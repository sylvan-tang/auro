package com.sylvan.hecate.common.util;

import com.google.common.math.DoubleMath;
import java.time.Duration;

public class InterestUtils {

  /**
   * 根据 computeTotal 函数中最终金额的递归计算可得
   *
   * <p>1. 令 x = Math.pow(1 + rate, days) 可得：
   *
   * <p>total(0) = 0;
   *
   * <p>total(times) = (total(times-1) + amount) * x;
   *
   * <p>total(times) = amount * Math.pow(x, times) + amount * Math.pow(x, times - 1) + ... + amount
   * * Math(x, 2) + amount * Math(x, 1)
   *
   * <p>2. 根据等比数列求和公式可得：
   *
   * <p>total(times) = amount * (Math.pow(x, times + 1) - x) / (x - 1)
   *
   * <p>3. 由于该方程得解非直接可得，但我们知道指数函数是单调函数，所以可以利用二分法进行求值
   *
   * <p>4. 当第一步中的 total(times) = amount * times * Math.pow(x, times) 时，求得 minX
   *
   * <p>5. 当第一步中的 total(times) = amount * times * Math.pow(x, 1) 时，求得 maxX
   *
   * <p>6. 不断使用二分法求得最接近 total 的值，此时得到的 x 为最佳解
   *
   * <p>7. 使用 computeBase(days, x) 求得 1+rate，进而求得 rate
   *
   * @param total: 最终总金额
   * @param amount: 定投金额
   * @param days : 定投周期(按天算）
   * @param times：定投次数
   * @return : 日利率
   */
  public static double computeRate(double total, double amount, int days, int times) {
    double minX = 0D;
    double maxX = total / (amount * times);
    while (!DoubleMath.fuzzyEquals(minX, maxX, 0.000000000001D)) {
      double x = (minX + maxX) / 2D;
      double totalV = amount * ((Math.pow(x, times + 1) - x) / (x - 1));
      int compare = DoubleMath.fuzzyCompare(total, totalV, 0.001D);
      if (compare == 0) {
        minX = x;
      } else if (compare == 1) {
        minX = x;
      } else {
        maxX = x;
      }
    }
    return computeBase(days, minX) - 1;
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
    return Math.pow(10D, Math.log10(value) / exponent);
  }

  /**
   * @param rate : 日利率
   * @param duration : 转换时间单位
   * @return ：目标时间单位化后的利率
   */
  public static double convertRate(double rate, Duration duration) {
    return Math.pow(1D + rate, duration.toDays()) - 1D;
  }

  /**
   * @param rate : 单位时间的利率
   * @param duration : 转换时间单位
   * @return ：日利率
   */
  public static double convertToRate(double rate, Duration duration) {
    return computeBase(duration.toDays(), 1D + rate) - 1D;
  }

  public static void main(String[] args) {
    System.out.println(convertRate(computeRate(2732.58, 500, 30, 5), Duration.ofDays(365)));

    System.out.println(convertRate(computeRate(2973.73, 300, 7, 9), Duration.ofDays(365)));
  }
}
