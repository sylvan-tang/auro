package com.sylvan.auro.services.netty.nio.channel;

import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.mockito.internal.util.io.IOUtil;

public class SocketChannelCase {
  private static final Charset charset = StandardCharsets.UTF_8;

  public void createFileChannel() throws IOException {
    String srcPath = "/Users/sylvan/codes/auro/README.md";
    String destFile = "/Users/sylvan/codes/auro/README-SOCKET.md";
    File file = new File(srcPath);
    if (!file.exists()) {
      return;
    }
    FileChannel fileChannel = new FileInputStream(file).getChannel();

    SocketChannel socketChannel = new NioSocketChannel();
    socketChannel.bind(new InetSocketAddress(8080));
    while (socketChannel.isActive()) {
      ByteBuffer fileNameByteBuffer = charset.encode(destFile);
      socketChannel.write(fileNameByteBuffer);
      ByteBuffer buffer = ByteBuffer.allocate(1024);
      buffer.putLong(file.length());
      buffer.flip();
      socketChannel.write(buffer);
      buffer.clear();
      System.out.println("开始传输文件");
      int length = 0;
      long progress = 0;
      while ((length = fileChannel.read(buffer)) > 0) {
        buffer.flip();
        socketChannel.write(buffer);
        buffer.clear();
        progress += length;
        System.out.println("| " + (100 * progress / file.length()) + "% |");
      }
      if (length == -1) {
        IOUtil.closeQuietly(fileChannel);
        socketChannel.shutdownOutput();
        socketChannel.close();
      }
    }
  }

  public static void main(String[] args) throws IOException {
    SocketChannelCase channelCase = new SocketChannelCase();
    channelCase.createFileChannel();
  }
}
