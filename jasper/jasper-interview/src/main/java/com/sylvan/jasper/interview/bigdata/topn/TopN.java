package com.sylvan.jasper.interview.bigdata.topn;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/** 从海量数据中，计算出出现频次 TOP N 的数据 */
public class TopN {
  private final int concurrency;
  private final String inputPath;
  private final List<String> middlePaths;
  private final List<FileWriter> fileWriters;
  private final List<BufferedWriter> bufferedWriters;
  private final ForkJoinPool forkJoinPool = new ForkJoinPool(10);

  public TopN(int concurrency, String inputPath) {
    this.concurrency = concurrency;
    this.inputPath = inputPath;
    this.middlePaths =
        IntStream.range(0, this.concurrency)
            .mapToObj(i -> String.format("%s.%s", inputPath, i))
            .collect(Collectors.toList());
    this.fileWriters =
        this.middlePaths.stream()
            .map(
                path -> {
                  try {
                    return new FileWriter(path);
                  } catch (IOException e) {
                    e.printStackTrace();
                  }
                  return null;
                })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    if (this.fileWriters.size() < this.middlePaths.size()) {
      throw new RuntimeException("Init fileWriters failed");
    }
    this.bufferedWriters =
        this.fileWriters.stream().map(BufferedWriter::new).collect(Collectors.toList());
  }

  public static void main(String[] args) throws IOException {
    long start = System.currentTimeMillis();
    TopN topN = new TopN(10000, "/Users/sylvan/codes/local/test.log");
    topN.topN(100).forEach((key, value) -> System.out.println(String.format("%s: %s", key, value)));
    System.out.println(
        String.format("End of topN with %s millis", System.currentTimeMillis() - start));
  }

  public Map<String, Integer> topN(int n) throws IOException {
    File file = new File(inputPath); // creates a new file instance
    FileReader fr = new FileReader(file); // reads the file
    BufferedReader br = new BufferedReader(fr); // creates a buffering character input stream
    String line;
    while ((line = br.readLine()) != null) {
      hashToFile(line);
    }
    fr.close(); // closes the stream and release the resources
    this.fileWriters.forEach(
        fileWriter -> {
          try {
            fileWriter.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
    List<CompletableFuture<Map<String, Integer>>> future =
        this.middlePaths.stream()
            .map(
                path ->
                    CompletableFuture.supplyAsync(
                        () -> {
                          try {
                            return countFromFile(path, n);
                          } catch (IOException e) {
                            e.printStackTrace();
                            return new HashMap<String, Integer>();
                          }
                        },
                        forkJoinPool))
            .collect(Collectors.toList());
    return future.stream()
        .reduce(
            CompletableFuture.completedFuture(new HashMap<>()),
            (m1, m2) ->
                CompletableFuture.supplyAsync(
                    () ->
                        Stream.concat(m1.join().entrySet().stream(), m2.join().entrySet().stream())
                            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                            .collect(Collectors.toList())
                            .subList(0, n)
                            .stream()
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)),
                    forkJoinPool))
        .join();
  }

  private void hashToFile(String line) {
    int i = Math.abs(line.hashCode() % this.concurrency);
    BufferedWriter writer = this.bufferedWriters.get(i);
    try {
      writer.write(line);
      writer.write("\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private Map<String, Integer> countFromFile(String inputPath, int n) throws IOException {
    Map<String, Integer> output = new HashMap<>();
    File file = new File(inputPath); // creates a new file instance
    FileReader fr = new FileReader(file); // reads the file
    BufferedReader br = new BufferedReader(fr); // creates a buffering character input stream
    String line;
    while ((line = br.readLine()) != null) {
      output.merge(line, 1, Integer::sum);
    }
    fr.close(); // closes the stream and release the resources
    return output.entrySet().stream()
        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
        .collect(Collectors.toList())
        .subList(0, n)
        .stream()
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }
}
