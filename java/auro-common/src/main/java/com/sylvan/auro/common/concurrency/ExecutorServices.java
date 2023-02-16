package com.sylvan.auro.common.concurrency;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExecutorServices {

  private ExecutorServices() {}

  private static final String BACKGROUND_EXECUTOR_NAME = "background-executor";
  public static final String IO_EXECUTOR_NAME = "io-executor";
  private static final String CPU_EXECUTOR_NAME = "cpu-executor";

  private static final Duration KEEP_ALIVE_TIME = Duration.ofMinutes(1);
  private static final int BACKGROUND_QUEUE_SIZE = 2000;
  private static final int IO_QUEUE_SIZE = 3000;
  private static final int CPU_QUEUE_SIZE = 500;

  protected static final Map<String, InstrumentedThreadPoolExecutor> NAME_TO_EXECUTOR =
      new HashMap<>();

  public static final ScheduledExecutorService REPORT_SCHEDULER =
      new ScheduledThreadPoolExecutor(
          Runtime.getRuntime().availableProcessors(), new ThreadPoolExecutor.AbortPolicy());

  public static final ScheduledExecutorService SCHEDULED_EXECUTOR =
      new ScheduledThreadPoolExecutor(
          Runtime.getRuntime().availableProcessors(), new ThreadPoolExecutor.AbortPolicy());

  public static final ExecutorService BACKGROUND =
      newThreadPoolExecutor(
          BACKGROUND_EXECUTOR_NAME,
          2 * Runtime.getRuntime().availableProcessors(),
          2,
          BACKGROUND_QUEUE_SIZE);

  /** 用于处理 IO 密集型任务 */
  public static final ExecutorService IO =
      newThreadPoolExecutor(
          IO_EXECUTOR_NAME, 4 * Runtime.getRuntime().availableProcessors(), 4, IO_QUEUE_SIZE);

  /** 用于处理 CPU 密集型任务 */
  public static final ExecutorService CPU =
      newThreadPoolExecutor(
          CPU_EXECUTOR_NAME, Runtime.getRuntime().availableProcessors() + 1, 1, CPU_QUEUE_SIZE);

  private static ExecutorService newThreadPoolExecutor(
      String executorName, int corePoolSize, int maxPoolSizeFactor, int workQueueSize) {
    ResizableBlockingQueue<Runnable> workQueue =
        new ResizableBlockingQueue<>(
            new LinkedBlockingQueue<>(), workQueueSize, workQueueSize / 2, workQueueSize * 2, 100);
    InstrumentedThreadPoolExecutor executor =
        new InstrumentedThreadPoolExecutor(
            executorName,
            corePoolSize,
            corePoolSize * maxPoolSizeFactor,
            KEEP_ALIVE_TIME,
            workQueue,
            REPORT_SCHEDULER);

    executor.prestartAllCoreThreads();

    // 注册 executor
    NAME_TO_EXECUTOR.put(executorName, executor);
    return executor;
  }
}
