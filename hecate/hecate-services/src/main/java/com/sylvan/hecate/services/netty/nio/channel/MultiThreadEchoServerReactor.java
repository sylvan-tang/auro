package com.sylvan.hecate.services.netty.nio.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.AllArgsConstructor;

public class MultiThreadEchoServerReactor {
  ServerSocketChannel serverSocket;
  AtomicInteger next = new AtomicInteger(0);
  Selector[] selectors = new Selector[2];

  SubReactor[] subReactors = null;

  MultiThreadEchoServerReactor() throws IOException {
    selectors[0] = Selector.open();
    selectors[1] = Selector.open();
    serverSocket = ServerSocketChannel.open();
    InetSocketAddress address = new InetSocketAddress("127.0.0.1", 8080);
    serverSocket.socket().bind(address);
    serverSocket.configureBlocking(false);
    SelectionKey sk = serverSocket.register(selectors[0], SelectionKey.OP_ACCEPT);
    sk.attach(new AcceptorHandler());
  }

  @AllArgsConstructor
  static class SubReactor implements Runnable {
    private final Selector selector;

    @Override
    public void run() {}
  }

  static class AcceptorHandler implements Runnable {

    @Override
    public void run() {}
  }
}
