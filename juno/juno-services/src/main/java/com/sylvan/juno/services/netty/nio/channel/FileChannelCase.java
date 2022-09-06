package com.sylvan.juno.services.netty.nio.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelCase {
  public static void createFileChannel() throws IOException {
    FileInputStream fis = new FileInputStream("/Users/sylvan/codes/auro/README.md");
    FileChannel inChannel = fis.getChannel();
    FileOutputStream fos = new FileOutputStream("/Users/sylvan/codes/auro/README-OUT.md");
    FileChannel outChannel = fos.getChannel();
    ByteBuffer byteBuf = ByteBuffer.allocate(512);
    int length = -1;
    while ((length = inChannel.read(byteBuf)) != -1) {
      byteBuf.flip();
      int outLength = 0;
      while ((outLength = outChannel.write(byteBuf)) != 0) {
        System.out.printf("写入字节数： %s%n", outLength);
      }
      byteBuf.clear();
    }
    outChannel.force(true);
  }

  public static void main(String[] args) throws IOException {
    FileChannelCase.createFileChannel();
  }
}
