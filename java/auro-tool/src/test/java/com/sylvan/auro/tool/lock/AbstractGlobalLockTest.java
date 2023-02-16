package com.sylvan.auro.tool.lock;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Assertions;

public abstract class AbstractGlobalLockTest {
  private static final int START_LATCH = 1;
  private static final int THREAD_COUNT = 5;
  protected static final String KEY = "test";
  private static final String HOLDER_JIM = "Jim";
  private static final String HOLDER_TONY = "Tony";

  protected void globalLockShouldWorkBetweenThread(GlobalLock lock, boolean isWorked)
      throws InterruptedException {
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
                boolean getLock =
                    lock.obtain(KEY, Thread.currentThread().getName(), Duration.ofHours(1));
                endLatch.countDown();
                if (getLock) {
                  lock.retain(KEY, Thread.currentThread().getName(), Duration.ofHours(1));
                  counter.incrementAndGet();
                  lock.release(KEY, Thread.currentThread().getName());
                }
              });
      thread.start();
    }
    startLatch.countDown();
    endLatch.await();
    if (isWorked) {
      Assertions.assertEquals(1, counter.get());
    }
  }

  protected void globalLockShouldRetainByHolder(GlobalLock lock) throws InterruptedException {
    Assertions.assertTrue(lock.obtain(KEY, HOLDER_JIM, Duration.ofHours(1)));
    Assertions.assertFalse(lock.retain(KEY, HOLDER_TONY, Duration.ofHours(1)));
    Assertions.assertTrue(lock.retain(KEY, HOLDER_JIM, Duration.ofMillis(1)));
    Thread.sleep(2);
    Assertions.assertTrue(lock.obtain(KEY, HOLDER_JIM, Duration.ofHours(1)), "续租失效之后可以被重新获得");
    Assertions.assertTrue(lock.release(KEY, HOLDER_JIM));
  }

  protected void globalLockShouldReleaseByHolder(GlobalLock lock) {
    Assertions.assertTrue(lock.obtain(KEY, HOLDER_JIM, Duration.ofHours(1)));
    Assertions.assertFalse(lock.release(KEY, HOLDER_TONY));
    Assertions.assertTrue(lock.release(KEY, HOLDER_JIM));
    Assertions.assertTrue(lock.obtain(KEY, HOLDER_JIM, Duration.ofHours(1)), "锁释放之后可以被重新获得");
    Assertions.assertTrue(lock.release(KEY, HOLDER_JIM));
  }

  protected void lockShouldBeObtainAfterTimeout(GlobalLock lock) throws InterruptedException {
    Assertions.assertTrue(lock.obtain(KEY, HOLDER_JIM, Duration.ofMillis(20)));
    Thread.sleep(1000);
    Assertions.assertTrue(
        lock.obtain(KEY, HOLDER_TONY, Duration.ofHours(1)), "在获得锁之后没有显式释放，超时后可以被其他线程获得");
    Assertions.assertTrue(lock.release(KEY, HOLDER_TONY));
  }

  protected void lockShouldBeRetrainAfterTimeout(GlobalLock lock) throws InterruptedException {
    Assertions.assertTrue(lock.obtain(KEY, HOLDER_JIM, Duration.ofMillis(20)));
    Thread.sleep(1000);
    Assertions.assertTrue(
        lock.retain(KEY, HOLDER_JIM, Duration.ofHours(1)),
        "在获得锁之后没有显式释放，在没被其他线程获得锁的前提下，超时后可以被自己续租");
    Assertions.assertTrue(lock.release(KEY, HOLDER_JIM));
  }
}
