package com.sylvan.soteria.infrastructure.spark;

/**
 * every test case that require SparkSession must be defined in one test file
 * because it will get unexpected results while running more than one Spark context in a single JVM
 */

import com.typesafe.config.{ Config, ConfigFactory }
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import wvlet.log.LogSupport

/**
 * SparkAPI will generate a global SparkSession and support for the whole spark job
 */
case class SparkAPI(appName: String) extends LogSupport {
  // ConfigFactory will find application.conf in resources and loading the config into Config object
  val config: Config = ConfigFactory.load()

  private val sparkSession: SparkSession = {
    // read master mode from config
    val masterMode: String = config.getString(s"spark.jobs.$appName.master")
    info(s"appName: $appName, masterMode: $masterMode")
    SparkSession
      .builder()
      .appName(appName)
      .master(masterMode)
      .enableHiveSupport()
      .getOrCreate()
  }

  def getSparkSession: SparkSession = {
    sparkSession
  }

  /**
   * run spark job and stop spark session
   * param: sparkJob is a method that require sparkSession and return nothing
   */
  def runAndStop(sparkJob: SparkSession => Unit): Unit = {
    sparkJob(sparkSession)
    sparkSession.stop()
  }

  /**
   * setting dynamic configurations into sparkSession so that runAndStop only require sparkSession
   * static configurations is suggested to setting in resources/application.conf and use ConfigFactory.load() to obtain
   */
  def setConf(key: String, value: String): Unit = {
    sparkSession.conf.set(key, value)
  }
}

object SparkAPI extends LogSupport {
  /**
   * load data as string rdd
   * @param filePath: local file:// path or hdfs:// path is supported
   * @return
   */
  def loadRDDFromFile(sparkSession: SparkSession, filePath: String): RDD[String] = {
    sparkSession.sparkContext.textFile(filePath)
  }
}
