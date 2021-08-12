package com.sylvan.hecate.common.concurrency;
/** @author sylvan */
public class TimedRunnable implements Runnable {

  private final Runnable original;
  private final long createTimeNs;
  private long startTimeNs;
  private long finishTimeNs;

  TimedRunnable(Runnable original) {
    this.original = original;
    this.createTimeNs = System.nanoTime();
    this.startTimeNs = -1;
    this.finishTimeNs = -1;
  }

  @Override
  public void run() {
    try {
      startTimeNs = System.nanoTime();
      original.run();
    } finally {
      finishTimeNs = System.nanoTime();
    }
  }

  long getTotalTimeNs() {
    if (finishTimeNs == -1) {
      return -1;
    }
    return finishTimeNs - createTimeNs;
  }

  long getExecutionTimeNs() {
    if (startTimeNs == -1 || finishTimeNs == -1) {
      return -1;
    }
    return finishTimeNs - startTimeNs;
  }

  long getAwaitTimeNs() {
    if (startTimeNs == -1) {
      return -1;
    }
    return startTimeNs - createTimeNs;
  }
}
