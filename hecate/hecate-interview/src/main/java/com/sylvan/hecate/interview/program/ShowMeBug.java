package com.sylvan.hecate.interview.program;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ShowMeBug {

  private static String decimal2binary(int decNum, int digit) {
    String binStr = "";
    for (int i = digit - 1; i >= 0; i--) {
      binStr += (decNum >> i) & 1;
    }
    return binStr;
  }

  private static String getMinNetwork(String ip1, String ip2) {
    List<Integer> ipList1 =
        Arrays.stream(ip1.split("\\.")).map(Integer::valueOf).collect(Collectors.toList());

    List<Integer> ipList2 =
        Arrays.stream(ip2.split("\\.")).map(Integer::valueOf).collect(Collectors.toList());

    int code = 0;
    List<Integer> ipAddress = new ArrayList<>();
    boolean isDifferent = false;
    for (int i = 0; i < ipList1.size(); i++) {
      if (isDifferent) {
        ipAddress.add(0);
        continue;
      }
      if (ipList1.get(i).equals(ipList2.get(i))) {
        code += 8;
        ipAddress.add(ipList1.get(i));
        continue;
      }
      String binary1 = decimal2binary(ipList1.get(i), 8);
      String binary2 = decimal2binary(ipList2.get(i), 8);
      int j = 0;
      StringBuilder minBinary = new StringBuilder();
      while (binary1.charAt(j) == binary2.charAt(j)) {
        minBinary.append(binary1.charAt(j));
        j += 1;
      }
      code += j;
      while (j < binary1.length()) {
        minBinary.append("0");
        j += 1;
      }
      ipAddress.add(Integer.parseUnsignedInt(minBinary.toString(), 2));
      isDifferent = true;
    }
    return ipAddress.stream().map(String::valueOf).collect(Collectors.joining(".")) + "/" + code;
  }

  public static void main(String[] args) {
    System.out.println(getMinNetwork("192.168.128.1", "192.168.255.2"));
    System.out.println(getMinNetwork("10.16.0.255", "10.16.0.2"));
  }
}
