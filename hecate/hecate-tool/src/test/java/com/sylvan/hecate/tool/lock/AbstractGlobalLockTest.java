package com.sylvan.hecate.tool.lock;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Assert;

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
    Assert.assertEquals(isWorked, counter.get() == 1);
  }

  protected void globalLockShouldRetainByHolder(GlobalLock lock) throws InterruptedException {
    Assert.assertTrue(lock.obtain(KEY, HOLDER_JIM, Duration.ofHours(1)));
    Assert.assertFalse(lock.retain(KEY, HOLDER_TONY, Duration.ofHours(1)));
    Assert.assertTrue(lock.retain(KEY, HOLDER_JIM, Duration.ofMillis(1)));
    Thread.sleep(2);
    Assert.assertTrue("续租失效之后可以被重新获得", lock.obtain(KEY, HOLDER_JIM, Duration.ofHours(1)));
    Assert.assertTrue(lock.release(KEY, HOLDER_JIM));
  }

  protected void globalLockShouldReleaseByHolder(GlobalLock lock) {
    Assert.assertTrue(lock.obtain(KEY, HOLDER_JIM, Duration.ofHours(1)));
    Assert.assertFalse(lock.release(KEY, HOLDER_TONY));
    Assert.assertTrue(lock.release(KEY, HOLDER_JIM));
    Assert.assertTrue("锁释放之后可以被重新获得", lock.obtain(KEY, HOLDER_JIM, Duration.ofHours(1)));
    Assert.assertTrue(lock.release(KEY, HOLDER_JIM));
  }

  protected void lockShouldBeObtainAfterTimeout(GlobalLock lock) throws InterruptedException {
    Assert.assertTrue(lock.obtain(KEY, HOLDER_JIM, Duration.ofMillis(20)));
    Thread.sleep(1000);
    Assert.assertTrue(
        "在获得锁之后没有显式释放，超时后可以被其他线程获得", lock.obtain(KEY, HOLDER_TONY, Duration.ofHours(1)));
    Assert.assertTrue(lock.release(KEY, HOLDER_TONY));
  }

  protected void lockShouldBeRetrainAfterTimeout(GlobalLock lock) throws InterruptedException {
    Assert.assertTrue(lock.obtain(KEY, HOLDER_JIM, Duration.ofMillis(20)));
    Thread.sleep(1000);
    Assert.assertTrue(
        "在获得锁之后没有显式释放，在没被其他线程获得锁的前提下，超时后可以被自己续租",
        lock.retain(KEY, HOLDER_JIM, Duration.ofHours(1)));
    Assert.assertTrue(lock.release(KEY, HOLDER_JIM));
  }
}
