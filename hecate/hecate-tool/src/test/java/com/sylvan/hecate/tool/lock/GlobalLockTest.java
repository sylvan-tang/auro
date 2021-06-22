package com.sylvan.hecate.tool.lock;

import com.sylvan.hecate.persistence.dao.GlobalLockDao;
import com.sylvan.hecate.tool.Bootstrap;
import com.sylvan.hecate.tool.lock.impl.GlobalLockMysqlImpl;
import com.sylvan.hecate.tool.lock.impl.GlobalLockMysqlSimpleImpl;
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
    globalLockDao.delete(AbstractGlobalLockTest.KEY);
  }

  @Test
  void testGlobalLockShouldWorkBetweenThread() throws InterruptedException {
    globalLockShouldWorkBetweenThread(globalLockMysql, true);
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

  @Test
  void testLockSimpleImpl() throws InterruptedException {
    globalLockShouldWorkBetweenThread(globalLockMysqlSimple, false);
  }
}
