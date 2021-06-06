package com.sylvan.hecate.tool.lock;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Assert;

public abstract class AbstractGlobalLockTest {
  private static final AtomicInteger counter = new AtomicInteger();
  private static final int START_LATCH = 1;
  private static final int THREAD_COUNT = 5;

  protected void globalLockShouldWorkBetweenThread(GlobalLock lock, boolean isWorked)
      throws InterruptedException {
    counter.getAndSet(0);
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
                boolean getLock = lock.obtain("test", "test", Duration.ofHours(1));
                endLatch.countDown();
                if (getLock) {
                  counter.incrementAndGet();
                  lock.release("test", "test");
                }
              });
      thread.start();
    }
    startLatch.countDown();
    endLatch.await();
    Assert.assertEquals(isWorked, counter.get() == 1);
  }

  protected void globalLockShouldRetainByHolder(GlobalLock lock) throws InterruptedException {
    Assert.assertTrue(lock.obtain("test", "test", Duration.ofHours(1)));
    Assert.assertFalse(lock.retain("test", "test1", Duration.ofHours(1)));
    Assert.assertTrue(lock.retain("test", "test", Duration.ofMillis(1)));
    Thread.sleep(2);
    Assert.assertTrue("续租失效之后可以被重新获得", lock.obtain("test", "test", Duration.ofHours(1)));
    Assert.assertTrue(lock.release("test", "test"));
  }

  protected void globalLockShouldReleaseByHolder(GlobalLock lock) {
    Assert.assertTrue(lock.obtain("test", "test", Duration.ofHours(1)));
    Assert.assertFalse(lock.release("test", "test1"));
    Assert.assertTrue(lock.release("test", "test"));
    Assert.assertTrue("锁释放之后可以被重新获得", lock.obtain("test", "test", Duration.ofHours(1)));
    Assert.assertTrue(lock.release("test", "test"));
  }
}
