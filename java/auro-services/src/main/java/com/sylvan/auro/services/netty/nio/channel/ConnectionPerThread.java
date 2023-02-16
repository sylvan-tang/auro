package com.sylvan.auro.services.netty.nio.channel;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import lombok.AllArgsConstructor;

public class ConnectionPerThread implements Runnable {
  @Override
  public void run() {
    try {
      ServerSocket serverSocket = new ServerSocket(8080);
      while (!Thread.interrupted()) {
        Socket socket = serverSocket.accept();
        Handler handler = new Handler(socket);
        new Thread(handler).start();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @AllArgsConstructor
  public static class Handler implements Runnable {
    final Socket socket;

    @Override
    public void run() {
      while (true) {
        byte[] input = new byte[1024];
        try {
          socket.getInputStream().read(input);
          byte[] output = null;
          socket.getOutputStream().write(output);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
