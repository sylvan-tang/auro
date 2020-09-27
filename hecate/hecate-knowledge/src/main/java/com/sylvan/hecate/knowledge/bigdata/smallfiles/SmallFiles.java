package com.sylvan.hecate.knowledge.bigdata.smallfiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SmallFiles {
  private static final List<String> FOLDER_TYPES = Arrays.asList("rm", "rsync", "walk");
  private final Path dirPath;
  private final int filesNum;
  private final int parallelism;

  public SmallFiles(String dirPath, int filesNum, int parallelism) throws IOException {
    this.filesNum = filesNum;
    this.parallelism = parallelism;
    this.dirPath = Paths.get(dirPath, String.valueOf(filesNum), String.valueOf(parallelism));
    if (!Files.exists(this.dirPath)) {
      Files.createDirectories(this.dirPath);
    }
    FOLDER_TYPES.forEach(
        folderType -> {
          Path path = Paths.get(this.dirPath.toString(), folderType);
          if (!Files.exists(path)) {
            try {
              Files.createDirectories(path);
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        });
  }

  /** 使用分布式生成方法生成多个小文件 */
  public void generateSmallFiles(boolean printInfo)
      throws ExecutionException, InterruptedException {
    long start = System.currentTimeMillis();
    int batchSize = filesNum / parallelism + 1;
    Stream<CompletableFuture<Long>> jobs =
        IntStream.range(0, parallelism)
            .boxed()
            .map(
                finalI ->
                    CompletableFuture.supplyAsync(
                        () -> {
                          long count = 0L;
                          for (int j = finalI * batchSize; j < (finalI + 1) * batchSize; j++) {
                            for (String folderType : FOLDER_TYPES) {
                              try {
                                Files.write(
                                    Paths.get(dirPath.toString(), folderType, j + ".txt"),
                                    String.format("%s\n", j).getBytes(),
                                    StandardOpenOption.CREATE);
                                count += 1L;
                              } catch (IOException e) {
                                e.printStackTrace();
                              }
                            }
                          }
                          return count;
                        }));
    long count =
        jobs.reduce(
                CompletableFuture.completedFuture(0L),
                (first, second) ->
                    CompletableFuture.supplyAsync(
                        () -> {
                          try {
                            return first.get() + second.get();
                          } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                          }
                          return 0L;
                        }))
            .get();

    if (printInfo) {
      System.out.printf(
          "| %s | %s | %s |", count, parallelism, (System.currentTimeMillis() - start));
    }
  }

  /** 遍历并删除小文件 */
  public void walk() throws IOException, ExecutionException, InterruptedException {
    long start = System.currentTimeMillis();
    Path walkPath = Paths.get(this.dirPath.toString(), "walk");
    Stream<CompletableFuture<Long>> jobs =
        Files.walk(walkPath)
            .filter(path -> !path.toString().endsWith(walkPath.toString()))
            .map(
                path ->
                    CompletableFuture.supplyAsync(
                        () -> {
                          try {
                            Files.deleteIfExists(path);
                            return 1L;
                          } catch (IOException e) {
                            e.printStackTrace();
                          }
                          return 0L;
                        }));
    long count =
        jobs.reduce(
                CompletableFuture.completedFuture(0L),
                (first, second) ->
                    CompletableFuture.supplyAsync(
                        () -> {
                          try {
                            return first.get() + second.get();
                          } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                          }
                          return 0L;
                        }))
            .get();
    System.out.printf("| %s | %s | %s |", count, parallelism, (System.currentTimeMillis() - start));
  }

  public static void generateFiles() {
    System.out.println("| 文件个数 | 并发数 | 创建时间 |");
    System.out.println("| ---- | ---- | ---- |");
    for (List<Integer> integers :
        Arrays.asList(
            Arrays.asList(1000, 1),
            Arrays.asList(1000, 10),
            Arrays.asList(10000, 10),
            Arrays.asList(20000, 20),
            Arrays.asList(100000, 20),
            Arrays.asList(200000, 20),
            Arrays.asList(1000000, 20),
            Arrays.asList(2000000, 20))) {
      try {
        SmallFiles smallFiles = new SmallFiles("/tmp/smalls/", integers.get(0), integers.get(1));
        smallFiles.generateSmallFiles(true);
        System.out.println();
      } catch (IOException | ExecutionException | InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public static void walkFiles() {
    System.out.println("| 文件个数 | 并发数 | walk 时间 |");
    System.out.println("| ---- | ---- | ---- |");
    for (List<Integer> integers :
        Arrays.asList(
            Arrays.asList(1000, 1),
            Arrays.asList(1000, 10),
            Arrays.asList(10000, 10),
            Arrays.asList(20000, 20),
            Arrays.asList(100000, 20),
            Arrays.asList(200000, 20),
            Arrays.asList(1000000, 20),
            Arrays.asList(2000000, 20))) {
      try {
        SmallFiles smallFiles = new SmallFiles("/tmp/smalls/", integers.get(0), integers.get(1));
        smallFiles.walk();
        System.out.println();
      } catch (IOException | InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) {
    //    generateFiles();
    walkFiles();
  }
}
