package com.sylvan.hecate.services.netty.nio.channel;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public class ReactorCase implements Runnable {
  Selector selector;
  ServerSocketChannel serverSocket;

  void EchoServerReactor() throws IOException {
    SelectionKey sk = serverSocket.register(selector, SelectionKey.OP_ACCEPT);
    sk.attach(new AcceptorHandler());
  }

  @Override
  public void run() {
    try {
      while (!Thread.interrupted()) {
        selector.select();
        Set<SelectionKey> selected = selector.selectedKeys();
        Iterator<SelectionKey> it = selected.iterator();
        while (it.hasNext()) {
          SelectionKey sk = it.next();
          dispatch(sk);
        }
        selected.clear();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  void dispatch(SelectionKey k) {
    Runnable handler = (Runnable) k.attachment();
    if (Objects.nonNull(handler)) {
      handler.run();
    }
  }

  static class AcceptorHandler implements Runnable {

    @Override
    public void run() {}
  }
}
