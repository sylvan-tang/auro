package com.sylvan.hecate.interview.program;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

class BufferedWordReader {
  private static final Set<Character> SEP_SET = new HashSet<>(Arrays.asList(' ', '\n'));
  private final BufferedReader reader;
  private String readerData = "";
  private boolean readerIsClosed = false;

  public BufferedWordReader(String path) {
    BufferedReader reader1 = null;
    try {
      reader1 = new BufferedReader(new FileReader(path));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    reader = reader1;
  }

  public String readWord() {
    if (readerIsClosed) {
      String word = readerData;
      readerData = "";
      return word;
    }
    String word = getReaderData();
    if (!word.isEmpty()) {
      return word;
    }

    char[] buf = new char[1024];
    int numRead = 0;
    try {
      numRead = reader.read(buf);
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (numRead != -1) {
      String readData = String.valueOf(buf, 0, numRead);
      readerData = readerData + readData;
    } else {
      try {
        reader.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
      readerIsClosed = true;
    }
    return readWord();
  }

  private String getReaderData() {
    int indexOfSep =
        IntStream.range(0, readerData.length())
            .boxed()
            .filter(i -> SEP_SET.contains(readerData.charAt(i)))
            .findFirst()
            .orElse(-1);

    String word = "";
    if (indexOfSep != -1) {
      word = readerData.substring(0, indexOfSep);
      readerData = readerData.substring(indexOfSep + 1);
    }
    return word;
  }
}
