package com.sylvan.juno.tool.lock;

import com.sylvan.juno.persistence.dao.GlobalLockDao;
import com.sylvan.juno.tool.Bootstrap;
import com.sylvan.juno.tool.lock.impl.GlobalLockMysqlImpl;
import com.sylvan.juno.tool.lock.impl.GlobalLockMysqlSimpleImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Bootstrap.class)
class GlobalLockTest extends AbstractGlobalLockTest {

  @Autowired private GlobalLockMysqlSimpleImpl globalLockMysqlSimple;

  @Autowired private GlobalLockMysqlImpl globalLockMysql;
  @Autowired private GlobalLockDao globalLockDao;

  @BeforeEach
  void setUp() {
    System.out.printf("Start at: %s", System.currentTimeMillis());
    globalLockDao.delete(AbstractGlobalLockTest.KEY);
  }

  @AfterEach
  void tearDown() {
    System.out.printf("End at: %s", System.currentTimeMillis());
  }

  @Test
  void testGlobalLockShouldWorkBetweenThread() throws InterruptedException {
    globalLockShouldWorkBetweenThread(globalLockMysql, true);
    globalLockDao.delete(AbstractGlobalLockTest.KEY);
    globalLockShouldWorkBetweenThread(globalLockMysqlSimple, false);
  }

  @Test
  void testGlobalLockShouldRetainByHolder() throws InterruptedException {
    globalLockShouldRetainByHolder(globalLockMysql);
  }

  @Test
  void testGlobalLockShouldReleaseByHolder() {
    globalLockShouldReleaseByHolder(globalLockMysql);
  }

  @Test
  void testLockShouldBeObtainAfterTimeout() throws InterruptedException {
    lockShouldBeObtainAfterTimeout(globalLockMysql);
  }

  @Test
  void testLockShouldBeRetrainAfterTimeout() throws InterruptedException {
    lockShouldBeRetrainAfterTimeout(globalLockMysql);
  }
}
