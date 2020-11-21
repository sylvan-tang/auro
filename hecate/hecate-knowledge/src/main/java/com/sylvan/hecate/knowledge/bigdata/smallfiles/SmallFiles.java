package com.sylvan.hecate.knowledge.bigdata.smallfiles;

import com.sylvan.hecate.common.util.CommandUtils;

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
    private final Path dirPath;
    private final int filesNum;
    private final int parallelism;

    public SmallFiles(String dirPath, int filesNum, int parallelism) {
        this.filesNum = filesNum;
        this.parallelism = parallelism;
        this.dirPath = Paths.get(dirPath);
    }

    /** 使用分布式生成方法生成多个小文件 */
    public void generateSmallFiles(boolean printInfo)
            throws ExecutionException, InterruptedException, IOException {
        if (!Files.exists(this.dirPath)) {
            Files.createDirectories(this.dirPath);
        }
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
                                                    for (int j = finalI * batchSize;
                                                            j < (finalI + 1) * batchSize;
                                                            j++) {
                                                        try {
                                                            Files.write(
                                                                    Paths.get(
                                                                            dirPath.toString(),
                                                                            j + ".txt"),
                                                                    String.format("%s\n", j)
                                                                            .getBytes(),
                                                                    StandardOpenOption.CREATE);
                                                            count += 1L;
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
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
                                                    } catch (InterruptedException
                                                            | ExecutionException e) {
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
        Stream<CompletableFuture<Long>> jobs =
                Files.walk(this.dirPath)
                        .filter(path -> !path.toString().equals(this.dirPath.toString()))
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
        System.out.printf(" %s |", (System.currentTimeMillis() - start));
    }

    public void rm() {
        long start = System.currentTimeMillis();
        CommandUtils.run(String.format("rm -rf %s", this.dirPath.toString()));
        System.out.printf(" %s |", (System.currentTimeMillis() - start));
    }

    public void rsync() {
        Path shellPath =
                Paths.get(
                        SmallFiles.class
                                .getResource("")
                                .getPath()
                                .replaceAll("target/classes", "src/main/java"),
                        "SmallFiles.sh");
        long start = System.currentTimeMillis();
        CommandUtils.run(shellPath.toString());
        System.out.printf(" %s |", (System.currentTimeMillis() - start));
    }

    public static void main(String[] args) {
        System.out.println("| 文件个数 | 并发数 | 创建时间 | rm 时间 | rsync 时间 | walk delete 时间 |");
        System.out.println("| ---- | ---- | ---- | ---- | ---- | ---- |");
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
                SmallFiles smallFiles =
                        new SmallFiles("/tmp/smalls/", integers.get(0), integers.get(1));

                /*
                为了研究Java如何处理大量小文件，首先编写一个生成大量小文件的函数，生成多个规格的包含小文件的文件夹
                 */
                smallFiles.generateSmallFiles(true);
                /*
                从网上给出的资料中查询到，使用 rsync 空文件夹到含有大量小文件的文件夹会比 rm 更高效，所以使用刚刚生成的文件夹分别实验一下 rm 和 rsync
                 */
                smallFiles.rm();
                smallFiles.generateSmallFiles(false);
                smallFiles.rsync();
                smallFiles.generateSmallFiles(false);

                /*
                使用 Java 的 File walk 分布式删除文件
                 */
                smallFiles.walk();
                System.out.println();

                /*
                最后，得出结论，在以下配置的电脑下运行，rsnyc 方式删除小文件最为高效，耗时报告在 SmallFiles.md 中记录：

                MacBook Pro (13-inch, 2017, Four Thunderbolt 3 Ports)

                3.1 GHz 双核Intel Core i5

                16 GB 2133 MHz LPDDR3

                Intel Iris Plus Graphics 650 1536 MB
                 */

            } catch (IOException | InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
