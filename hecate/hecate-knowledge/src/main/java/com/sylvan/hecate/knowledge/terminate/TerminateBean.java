package com.sylvan.hecate.knowledge.terminate;

import javax.annotation.PreDestroy;

/** @author sylvan */
public class TerminateBean {

  /** 配置上PreDestroy，用于处理 bean 销毁前需要完成的操作，在这个函数里，可以将线程未执行的任务完成 */
  @PreDestroy
  public void preDestroy() {
    System.out.println("TerminalBean is destroyed");
  }
}
