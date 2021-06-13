package com.sylvan.hecate.tool.lock;

import com.sylvan.hecate.tool.Bootstrap;
import com.sylvan.hecate.tool.lock.impl.GlobalLockMysqlSimpleImpl;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Bootstrap.class)
public class GlobalLockTest extends AbstractGlobalLockTest {
  @Autowired private List<GlobalLock> lockImplList;

  @Test
  public void testLockImpl() throws InterruptedException {
    for (GlobalLock lockImpl : lockImplList) {
      globalLockShouldWorkBetweenThread(lockImpl, !(lockImpl instanceof GlobalLockMysqlSimpleImpl));
      if (!(lockImpl instanceof GlobalLockMysqlSimpleImpl)) {
        globalLockShouldRetainByHolder(lockImpl);
        globalLockShouldReleaseByHolder(lockImpl);
      }
    }
  }
}
