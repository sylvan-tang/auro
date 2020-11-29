package com.sylvan.hecate.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * 运行 shell 命令行并获得输出
 *
 * @author sylvan
 */
public class CommandUtils {
  public static String run(String command) {
    Scanner input;
    StringBuilder result = new StringBuilder();
    Process process;
    try {
      process = Runtime.getRuntime().exec(command);
    } catch (IOException e) {
      e.printStackTrace();
      return "";
    }
    try {
      // 等待命令执行完成
      process.waitFor();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    InputStream is = process.getInputStream();
    input = new Scanner(is);
    while (input.hasNextLine()) {
      result.append(input.nextLine()).append("\n");
    }
    // 加上命令本身，打印出来
    result.insert(0, command + "\n");
    return result.toString();
  }
}
