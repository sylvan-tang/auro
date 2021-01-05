// help: https://www.scala-sbt.org/1.x/docs/index.html

// before building this project, make sure you had setting ~/.sbt/credentials.properties properly
//$ cat ~/.sbt/credentials.properties
// realm=Artifactory Realm
// host=localhost
// user=admin
// password=password

import scalariform.formatter.preferences._

ThisBuild / scalaVersion := "2.11.12" // scalafix 0.9.5 only work on scala 2.11.12 or 2.12.8
ThisBuild / organization := "com.sylvan.gaea"
ThisBuild / version := "0.1.0-SNAPSHOT"

// enable coverage so that you can report test coverage after build package, using: `sbt coverageReport && sbt coverageAggregate`
coverageEnabled := true
//coveragaeamum := 0
coverageFailOnMinimum := true

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

// every project must import this setting except root
// using: `.settings(commonSettings: _*)`
val commonSettings = Seq(
  // for auto reformat
  scalariformPreferences := scalariformPreferences.value
    .setPreference(AlignSingleLineCaseStatements, true)
    .setPreference(DoubleIndentConstructorArguments, true)
    .setPreference(DanglingCloseParenthesis, Force)
    .setPreference(AlignArguments, true)
    .setPreference(AlignParameters, true)
    .setPreference(MultilineScaladocCommentsStartOnFirstLine, false)
    .setPreference(NewlineAtEndOfFile, true),

  // common libraries
  libraryDependencies ++= testSeq ++ jacksonSeq ++ logSeq ++ configSeq ++ akkaSeq ++ sparkSeq ++ httpSeq ++ elasticSeq,

  // remove unused stuff
  addCompilerPlugin(scalafixSemanticdb), // enable SemanticDB
  scalacOptions ++= List(
    "-Yrangepos" // required by SemanticDB compiler plugin
    , "-Ywarn-unused" // required by `RemoveUnused` rule
    , "-Ywarn-unused-import" // required by `RemoveUnused` rule
  )
)

// =========== common library start ===========
lazy val testSeq = Seq(
  "org.scalatest" %% "scalatest" % "3.0.5" % Test
)

lazy val configSeq = Seq(
  // https://mvnrepository.com/artifact/com.typesafe/config
  "com.typesafe" % "config" % "1.3.4"
)

lazy val jacksonSeq = Seq(
  //jackson强制降级为spark匹配版本, 避免版本冲突
  "com.fasterxml.jackson.core" % "jackson-annotations" % "2.6.7" force(),
  "com.fasterxml.jackson.core" % "jackson-core" % "2.7.9" force(),
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.6.7.1" force(),
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.6.7.1" force()
)

// link: https://wvlet.org/airframe/docs/airframe-log.html
// enable: simple logging framework
lazy val logSeq = Seq(
  "org.wvlet.airframe" %% "airframe-log" % "0.50"
)

lazy val akkaHttpVersion = "10.1.8"
lazy val akkaVersion = "2.5.23"
lazy val akkaSeq = Seq(
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-xml" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-remote" % akkaVersion,
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
  "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % Test,
  "org.scalatest" %% "scalatest" % "3.0.5" % Test
)

lazy val sparkSeq = Seq(
  "org.apache.spark" %% "spark-core" % "2.4.0" % "provided",
  "org.apache.spark" %% "spark-streaming" % "2.4.0" % "provided",
  "org.apache.spark" %% "spark-mllib" % "2.4.0" % "provided",
  "org.apache.spark" %% "spark-sql" % "2.4.0" % "provided",
  "org.apache.spark" %% "spark-hive" % "2.4.0" % "provided"
)

lazy val http4sVersion = "0.18.23"
lazy val httpSeq = Seq(
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "org.http4s" %% "http4s-blaze-client" % http4sVersion
)

// https://www.elastic.co/guide/en/elasticsearch/hadoop/current/spark.html
// https://github.com/elastic/elasticsearch-hadoop
lazy val elasticSeq = Seq(
  "org.elasticsearch" % "elasticsearch-hadoop" % "7.3.0"
)

// =========== common library ends ===========

// 启动程序
// root project will be build automatically
// add all sub-project into aggregate if you want to build it when building root
// aggregation will run the aggregated tasks in parallel and with no defined ordering between them

lazy val root = (project in file("."))
  .aggregate(
    // 给启动程序打包
    gaeaSmallFiles,
    // 为了能在编译的时候触发测试，所有子项目也必须添加到 aggregate 中
    gaeaBook,
    gaeaCareer,
    gaeaCommon,
    gaeaFinancial,
    gaeaInfrastructure,
    gaeaTool
  ).settings(commonSettings: _*)
  .settings(
    name := "gaea-root"
  )

// 具体的启动类
lazy val gaeaSmallFiles = (project in file("./start/small-files"))
  .settings(commonSettings: _*)
  .dependsOn(gaeaTool)
  .settings(
    name := "gaea-small-files"
  )


// 书籍相关分享
lazy val gaeaBook = (project in file("./gaea-book"))
  .settings(commonSettings: _*)
  .settings(
    name := "gaea-book"
  )

// 职业生涯相关分享
lazy val gaeaCareer = (project in file("./gaea-career"))
  .settings(commonSettings: _*)
  .settings(
    name := "gaea-career"
  )

// 基础语法分享
lazy val gaeaCommon = (project in file("./gaea-common"))
  .settings(commonSettings: _*)
  .settings(
    name := "gaea-common"
  )

// 金融分享
lazy val gaeaFinancial = (project in file("./gaea-financial"))
  .settings(commonSettings: _*)
  .dependsOn(gaeaCommon)
  .settings(
    name := "gaea-financial"
  )

// 公共第三方框架
lazy val gaeaInfrastructure = (project in file("./gaea-infrastructure"))
  .settings(commonSettings: _*)
  .dependsOn(gaeaCommon)
  .settings(
    name := "gaea-infrastructure"
  )

// 第三方框架分享
lazy val gaeaTool = (project in file("./gaea-tool"))
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= sparkSeq ++ elasticSeq)
  .dependsOn(gaeaCommon)
  .dependsOn(gaeaInfrastructure)
  .settings(
    name := "gaea-tool"
  )

