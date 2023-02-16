package com.sylvan.auro.common.concurrency;

import com.google.common.base.Preconditions;
import java.time.Duration;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/** @author sylvan */
@Slf4j
public class InstrumentedThreadPoolExecutor extends ThreadPoolExecutor {

  private static final double NANOSECONDS_PER_MILLISECOND = TimeUnit.MILLISECONDS.toNanos(1);

  private final String executorName;

  public InstrumentedThreadPoolExecutor(
      String executorName,
      int corePoolSize,
      int maximumPoolSize,
      Duration keepAliveTime,
      ResizableBlockingQueue<Runnable> workQueue,
      ScheduledExecutorService metricReporter) {
    super(
        corePoolSize, maximumPoolSize, keepAliveTime.toMillis(), TimeUnit.MILLISECONDS, workQueue);

    this.executorName = executorName;

    metricReporter.scheduleAtFixedRate(this::doReport, 30, 30, TimeUnit.SECONDS);
  }

  @Override
  public void execute(Runnable command) {
    super.execute(new TimedRunnable(command));
  }

  @Override
  protected void afterExecute(Runnable r, Throwable t) {
    Preconditions.checkArgument(r instanceof TimedRunnable);

    super.afterExecute(r, t);

    TimedRunnable timedRunnable = (TimedRunnable) r;
    log.debug(
        "{} await-time {}",
        executorName,
        timedRunnable.getAwaitTimeNs() / NANOSECONDS_PER_MILLISECOND);
    log.debug(
        "{} execution-time {}",
        executorName,
        timedRunnable.getExecutionTimeNs() / NANOSECONDS_PER_MILLISECOND);
    log.debug(
        "{} total-execution-time {}",
        executorName,
        timedRunnable.getTotalTimeNs() / NANOSECONDS_PER_MILLISECOND);
  }

  private int adjustSize(int desiredSize, int size) {
    int absDiff = Math.min(Math.abs(desiredSize - size), Math.round(size * 0.1f));
    if (desiredSize > size) {
      return size + absDiff;
    } else if (desiredSize < size) {
      return size - absDiff;
    } else {
      return size;
    }
  }

  public int resetCoreSize(int corePoolSize) {
    int newCorePoolSize = adjustSize(corePoolSize, getCorePoolSize());
    setCorePoolSize(newCorePoolSize);
    return newCorePoolSize;
  }

  public int resetMaximumPoolSize(int maximumPoolSize) {
    int newMaximumPoolSize = adjustSize(maximumPoolSize, getMaximumPoolSize());
    setMaximumPoolSize(newMaximumPoolSize);
    return newMaximumPoolSize;
  }

  public int resetQueueSize(int queueSize) {
    ResizableBlockingQueue<Runnable> workQueue = (ResizableBlockingQueue<Runnable>) getQueue();
    return workQueue.adjustCapacity(queueSize);
  }

  /** 执行监控相关操作 */
  private void doReport() {
    log.debug("{} queue-size {}", executorName, getQueue().size());
    log.debug("{} pool-size {}", executorName, getPoolSize());
    log.debug("{} active-size {}", executorName, getActiveCount());
  }
}
