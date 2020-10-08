package com.sylvan.gaea.bigdata.files

import com.sylvan.gaea.app.bigdata.files.{ SmallFiles, SmallFilesSetting }
import com.sylvan.gaea.infrastructure.spark.SparkAPI
import wvlet.log.LogSupport

object Main extends LogSupport {
  def main(args: Array[String]): Unit = {
    //    val generator = SparkAPI("SmallFilesGenerator")
    //    generator.setConf(SmallFilesSetting.folderPath, args.apply(0))
    //    generator.setConf(SmallFilesSetting.dataNumConf, args.apply(1))
    //    generator.setConf(SmallFilesSetting.fileNumConf, args.apply(2))
    //    generator.runAndStop(SmallFiles.generateFiles)

    val reader = SparkAPI("SmallFilesReader")
    reader.setConf(SmallFilesSetting.folderPath, args.apply(0))
    reader.setConf(SmallFilesSetting.dataNumConf, args.apply(1))
    reader.setConf(SmallFilesSetting.fileNumConf, args.apply(2))
    reader.setConf(SmallFilesSetting.coalesceNumConf, args.apply(3))
    reader.runAndStop(SmallFiles.readFiles)
  }
}
