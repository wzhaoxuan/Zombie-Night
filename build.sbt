
ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.19"

lazy val root = (project in file("."))
  .settings(
    name := "scalaFXIntro",
    libraryDependencies ++= Seq(
      "org.scalafx" %% "scalafx" % "8.0.192-R14",
      "org.scalafx" %% "scalafxml-core-sfx8" % "0.5",
      "org.apache.derby" % "derby" % "10.14.2.0",
      "org.scalikejdbc" %% "scalikejdbc" % "4.0.0",
      "ch.qos.logback" % "logback-classic" % "1.2.3"
    )
  )
addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full)

