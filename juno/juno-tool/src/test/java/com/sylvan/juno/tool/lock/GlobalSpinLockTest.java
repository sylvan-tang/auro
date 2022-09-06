package com.sylvan.juno.tool.lock;

import com.sylvan.juno.tool.Bootstrap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Bootstrap.class)
class GlobalSpinLockTest {
  private static final int START_LATCH = 1;
  private static final int THREAD_COUNT = 5;
  private static final String KEY = "test";

  @Autowired private GlobalSpinLock globalSpinLock;

  @Test
  void spinLockShouldWorkBetweenThread() throws InterruptedException {
    AtomicInteger counter = new AtomicInteger();
    AtomicLongArray obtainTimestamps = new AtomicLongArray(THREAD_COUNT);
    final CountDownLatch startLatch = new CountDownLatch(START_LATCH);
    final CountDownLatch endLatch = new CountDownLatch(THREAD_COUNT);
    for (int i = 0; i < THREAD_COUNT; ++i) {
      Thread thread =
          new Thread(
              () -> {
                try {
                  startLatch.await();
                } catch (Exception ex) {
                  ex.printStackTrace();
                }
                try {
                  globalSpinLock.spinLock(KEY);
                  obtainTimestamps.compareAndSet(counter.get(), 0L, System.currentTimeMillis());
                  counter.incrementAndGet();
                } catch (Exception ex) {
                  ex.printStackTrace();
                }
                try {
                  globalSpinLock.spinUnlock(KEY);
                } catch (Exception ex) {
                  ex.printStackTrace();
                }
                endLatch.countDown();
              });
      thread.start();
    }
    startLatch.countDown();
    endLatch.await();
    Assertions.assertEquals(THREAD_COUNT, counter.get());
    Assertions.assertEquals(
        THREAD_COUNT,
        IntStream.range(0, THREAD_COUNT)
            .boxed()
            .map(obtainTimestamps::get)
            .collect(Collectors.toSet())
            .size());
  }
}
