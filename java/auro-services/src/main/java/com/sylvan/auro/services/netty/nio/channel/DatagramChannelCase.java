package com.sylvan.auro.services.netty.nio.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;
import org.assertj.core.util.DateUtil;

public class DatagramChannelCase {

  public void createFileChannel() throws IOException {
    DatagramChannel datagramChannel = DatagramChannel.open();
    datagramChannel.configureBlocking(false);

    ByteBuffer buffer = ByteBuffer.allocate(1024);

    Scanner scanner = new Scanner(System.in);

    while (scanner.hasNext()) {
      String next = scanner.next();
      buffer.put((DateUtil.now() + " >> " + next).getBytes());
      buffer.flip();
      datagramChannel.send(buffer, new InetSocketAddress("localhost", 80));
      buffer.clear();
    }
    datagramChannel.close();
  }

  public static void main(String[] args) throws IOException {
    DatagramChannelCase channelCase = new DatagramChannelCase();
    channelCase.createFileChannel();
  }
}
