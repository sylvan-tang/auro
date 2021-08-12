package com.sylvan.hecate.tool.lock;

import com.sylvan.hecate.tool.Bootstrap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
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
                globalSpinLock.spinLock(KEY);
                counter.incrementAndGet();
                globalSpinLock.spinUnlock(KEY);
              });
      thread.start();
    }
    startLatch.countDown();
    endLatch.await();
    Assertions.assertEquals(THREAD_COUNT, counter.get());
  }
}
