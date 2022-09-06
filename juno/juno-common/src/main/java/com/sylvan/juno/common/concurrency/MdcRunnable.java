package com.sylvan.juno.common.concurrency;

import java.util.Map;
import org.slf4j.MDC;
import org.springframework.util.CollectionUtils;

/** @author sylvan */
public class MdcRunnable implements Runnable {

  private final Runnable original;
  private final Map<String, String> mdcContext;

  public MdcRunnable(Runnable original) {
    this.original = original;
    this.mdcContext = MDC.getCopyOfContextMap();
  }

  @Override
  public void run() {
    try {
      setContextMap(mdcContext);
      original.run();
    } finally {
      MDC.clear();
    }
  }

  private static void setContextMap(Map<String, String> contextMap) {
    if (CollectionUtils.isEmpty(contextMap)) {
      MDC.clear();
    } else {
      MDC.setContextMap(contextMap);
    }
  }
}
