package com.sylvan.juno.services.netty.nio.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class IOSelectorCase {
  public void readFile() throws IOException {
    Selector selector = Selector.open();
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    serverSocketChannel.configureBlocking(false);
    serverSocketChannel.bind(new InetSocketAddress(8080));
    serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    while (selector.select() > 0) {
      Set<SelectionKey> selectedKeys = selector.selectedKeys();
      Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
      SocketChannel socketChannel;
      while (keyIterator.hasNext()) {
        SelectionKey key = keyIterator.next();
        if (key.isAcceptable()) {
          System.out.println("监听到新链接");
          socketChannel = serverSocketChannel.accept();
          socketChannel.configureBlocking(false);
          socketChannel.register(selector, SelectionKey.OP_READ);
        } else if (key.isConnectable()) {
          System.out.println("链接成功");
          socketChannel = (SocketChannel) key.channel();
          ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
          int length = 0;
          while ((length = socketChannel.read(byteBuffer)) > 0) {
            byteBuffer.flip();
            System.out.println(new String(byteBuffer.array(), 0, length));
            byteBuffer.clear();
          }
          socketChannel.close();
        }
        //                else if (key.isReadable()) {
        //                    System.out.println("可读通道");
        //                } else if (key.isWritable()) {
        //                    System.out.println("可写通道");
        //                }
        keyIterator.remove();
      }
      serverSocketChannel.close();
    }
  }

  public static void main(String[] args) throws IOException {
    IOSelectorCase ioSelectorCase = new IOSelectorCase();
    ioSelectorCase.readFile();
  }
}
