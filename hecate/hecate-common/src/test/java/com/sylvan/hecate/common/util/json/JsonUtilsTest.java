package com.sylvan.hecate.common.util.json;

import com.sylvan.hecate.common.util.JsonUtils;
import java.io.IOException;
import java.util.Collections;
import org.junit.Assert;
import org.junit.Test;

public class JsonUtilsTest {
  @Test
  public void testMarshal() throws IOException {
    Assert.assertEquals("2", JsonUtils.marshal(2));
    Assert.assertEquals("{\"key\":2}", JsonUtils.marshal(Collections.singletonMap("key", 2)));
    //        TODO: FIX dateTime format
    //        Assert.assertEquals("2020", JsonUtils.marshal(LocalDateTime.now()));
  }
}
