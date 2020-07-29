package com.sylvan.jasper.knowledge.thread;

import lombok.AllArgsConstructor;

/**
 * @author chen.suna
 * @date 2020/7/29
 */
@AllArgsConstructor
public class ThreadScheduling extends Thread {
  private final String id;

  public void doCalc(int i) {
    System.out.println(id + " do calc for " + i);
  }

  @Override
  public void run() {
    for (int i = 0; i < 10; i++) {
      doCalc(i);
    }
  }
}
