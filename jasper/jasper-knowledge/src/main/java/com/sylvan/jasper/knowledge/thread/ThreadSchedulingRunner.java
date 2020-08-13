package com.sylvan.jasper.knowledge.thread;

/**
 * @author sylvan
 * @date 2020/7/29
 */
public class ThreadSchedulingRunner {

  public static void main(String[] args) {
    ThreadScheduling t1 = new ThreadScheduling("Thread 1");
    t1.start();
    ThreadScheduling t2 = new ThreadScheduling("Thread 2");
    t2.start();
    ThreadScheduling t3 = new ThreadScheduling("Thread 3");
    t3.start();
  }
}
