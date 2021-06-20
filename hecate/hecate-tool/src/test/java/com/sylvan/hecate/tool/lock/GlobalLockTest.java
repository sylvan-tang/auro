package com.sylvan.hecate.tool.lock;

import com.sylvan.hecate.persistence.dao.GlobalLockDao;
import com.sylvan.hecate.tool.Bootstrap;
import com.sylvan.hecate.tool.lock.impl.GlobalLockMysqlSimpleImpl;
import java.util.List;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Bootstrap.class)
public class GlobalLockTest extends AbstractGlobalLockTest {
  @Autowired private List<GlobalLock> lockImplList;
  @Autowired private GlobalLockDao globalLockDao;

  @Ignore
  @Test
  public void testLockImpl() throws InterruptedException {
    for (GlobalLock lockImpl : lockImplList) {
      globalLockDao.delete(AbstractGlobalLockTest.KEY);
      globalLockShouldWorkBetweenThread(lockImpl, !(lockImpl instanceof GlobalLockMysqlSimpleImpl));
      if (!(lockImpl instanceof GlobalLockMysqlSimpleImpl)) {
        globalLockDao.delete(AbstractGlobalLockTest.KEY);
        globalLockShouldRetainByHolder(lockImpl);
        globalLockDao.delete(AbstractGlobalLockTest.KEY);
        globalLockShouldReleaseByHolder(lockImpl);
        globalLockDao.delete(AbstractGlobalLockTest.KEY);
        lockShouldBeObtainAfterTimeout(lockImpl);
        globalLockDao.delete(AbstractGlobalLockTest.KEY);
        lockShouldBeRetrainAfterTimeout(lockImpl);
      }
    }
  }
}
