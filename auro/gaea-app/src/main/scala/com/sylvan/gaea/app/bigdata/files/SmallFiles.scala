package com.sylvan.gaea.app.bigdata.files

import org.apache.spark.sql.SparkSession
import wvlet.log.LogSupport

object SmallFiles extends LogSupport {
  def generateFiles(sparkSession: SparkSession): Unit = {
    val start: Long = System.currentTimeMillis()
    sparkSession.sparkContext.parallelize(
      0 to sparkSession.conf.get(SmallFilesSetting.dataNumConf).toInt
    ).repartition(sparkSession.conf.get(SmallFilesSetting.fileNumConf).toInt)
      .saveAsTextFile(s"${sparkSession.conf.get(SmallFilesSetting.folderPath)}")
    logger.info(s"cost ${System.currentTimeMillis() - start}ms to generate ${sparkSession.conf.get(SmallFilesSetting.fileNumConf)} files of ${sparkSession.conf.get(SmallFilesSetting.dataNumConf)} lines.")
  }

  def readFiles(sparkSession: SparkSession): Unit = {
    val start: Long = System.currentTimeMillis()
    val sparkText = sparkSession.sparkContext.textFile(
      sparkSession.conf.get(SmallFilesSetting.folderPath)
    )
    val coalesceNum = sparkSession.conf.get(SmallFilesSetting.coalesceNumConf).toInt
    if (coalesceNum > 0) {
      /*
      使用 coalesce 尝试优化一个具有 200000 行数据，100000 个文件的数据集的读取，发现在不同 coalesce 数下，计算的时间如下：
      不设置 coalesce：20/10/09 08:37:40 INFO SmallFiles: cost 622483ms to fine max num 200000.
      coalesce = 10000: 20/10/09 09:08:01 INFO SmallFiles: cost 639200ms to fine max num 200000.
      coalesce = 1000: 20/10/09 09:17:53 INFO SmallFiles: cost 587671ms to fine max num 200000.
      coalesce = 100: 20/10/09 09:27:44 INFO SmallFiles: cost 585527ms to fine max num 200000.
      coalesce = 10: 20/10/09 09:38:00 INFO SmallFiles: cost 611345ms to fine max num 200000.
      结论如下：设置 coalesce 无法优化本地大量小文件的读取时间
      TODO: 使用 hdfs 实验，看是否因为在读取前合并了小文件，而优化了传输时间
       */
      sparkText.coalesce(coalesceNum)
    }
    val maxData = sparkText
      .map(
        data => data.toInt
      ).reduce((x1, x2) => math.max(x1, x2))
    logger.info(s"cost ${System.currentTimeMillis() - start}ms to fine max num $maxData.")
  }
}
