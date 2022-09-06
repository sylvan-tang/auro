package com.sylvan.juno.tool.protocol;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * external-link: https://www.ietf.org/rfc/rfc4122.txt
 *
 * <p>实现全局唯一标示符的算法协议
 */
public class UUIDGenerator {
  private UUID uuid = UUID.randomUUID();

  public static void main(String[] args) {
    System.out.println(System.currentTimeMillis());
    System.out.println(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(365 * 100));
  }
}
