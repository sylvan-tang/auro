package com.sylvan.auro.persistence.dao;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.sylvan.auro.persistence.TestBoostrap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

@Disabled(value = "TODO: use MockProvider to test dao")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestBoostrap.class)
@Rollback
@Transactional(rollbackFor = Throwable.class)
@TestExecutionListeners({DbUnitTestExecutionListener.class})
@DatabaseSetup("/mock/global_lock.xml")
class GlobalLockDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

  @Autowired private GlobalLockDao globalLockDao;

  @Test
  void globalLockDaoShouldReturnObjectWhenQueryById() {
    Assertions.assertNotNull(globalLockDao.findById(1));
    Assertions.assertNull(globalLockDao.findById(2));
  }
}
