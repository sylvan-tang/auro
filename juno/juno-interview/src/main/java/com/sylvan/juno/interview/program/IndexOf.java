package com.sylvan.juno.interview.program;

public class IndexOf {
  public static int indexOf(char[] charArray, char[] subArray) {
    for (int i = 0; i <= charArray.length - subArray.length; i++) {
      int sameSize = 0;
      while (sameSize < subArray.length && charArray[i + sameSize] == subArray[sameSize]) {
        sameSize++;
      }
      if (sameSize == subArray.length - 1) {
        return i;
      }
    }
    return -1;
  }
}
