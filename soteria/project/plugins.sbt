// link: https://github.com/sbt/sbt-assembly
// enable: sbt packaging
// shell command: sbt clean assembly
// if sbt clean assembly can't run properly
// please make sure you have read topone-gemini/README.mde and install and setting environment properly first
addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.9")
// link: https://github.com/sbt/sbt-scalariform
// enable: scala code reforming when run `sbt clean assembly`
addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.8.2")
// link: https://github.com/sbt/sbt-native-packager
// enable: SBT native packaging
addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.3.15")

// link: https://github.com/jrudolph/sbt-dependency-graph#main-tasks
// enable: visualize project's dependencies
addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.9.2")

// link: https://github.com/spray/sbt-revolver
// enable: a super-fast development turnaround for your Scala applications
addSbtPlugin("io.spray" % "sbt-revolver" % "0.9.1")

// link: http://www.scalastyle.org/sbt.html
// enable: scala code style checking
// shell command: sbt scalastyle
addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "1.0.0")

// link: https://github.com/scoverage/sbt-scoverage
// enable: test coverage report
// shell command: sbt coverageReport
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.6.0")

// link: https://scalacenter.github.io/scalafix/docs/users/installation.html
// enable: remove unused import in code
// shell command: sbt "scalafix RemoveUnused"
// Defaults:
//          RemoveUnused.imports = true
//          RemoveUnused.privates = true
//          RemoveUnused.locals = true
addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.9.5")
