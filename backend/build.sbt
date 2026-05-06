name := """basicframework"""

version := "1.0-SNAPSHOT"

resolvers ++= Seq(
  "Maven Central" at "https://repo1.maven.org/maven2/",
  "JCenter" at "https://jcenter.bintray.com/"
)

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean).enablePlugins(PlayScala)

scalaVersion := "2.12.12"

libraryDependencies += guice

// https://mvnrepository.com/artifact/commons-codec/commons-codec
libraryDependencies += "commons-codec" % "commons-codec" % "1.17.1"
// https://mvnrepository.com/artifact/com.amazonaws/aws-java-sdk-s3
libraryDependencies += "com.amazonaws" % "aws-java-sdk-s3" % "1.12.772"

libraryDependencies ++= Seq(
  javaJdbc,
  javaWs
)


libraryDependencies += "com.mysql" % "mysql-connector-j" % "8.4.0"
libraryDependencies ++= Seq(
  "com.google.code.gson" % "gson" % "2.6.2",
  "org.projectlombok" % "lombok" % "1.18.34",
  "javax.mail" % "mail" % "1.4"
)

libraryDependencies += filters

PlayKeys.fileWatchService := play.dev.filewatch.FileWatchService.polling(500)

PlayKeys.devSettings := Seq("play.akka.dev-mode.akka.http.parsing.max-uri-length" -> "20480")

PlayKeys.devSettings += "play.server.http.port" -> "9037"

fork in run := true
javaOptions += "-Djdk.tls.client.protocols=TLSv1.2"

libraryDependencies += "org.apache.commons" % "commons-email" % "1.5"

// Test dependencies (required by existing FunctionalTest and UnitTest)
libraryDependencies += "org.assertj"    % "assertj-core" % "3.24.2" % Test
libraryDependencies += "org.awaitility" % "awaitility"   % "3.1.6"  % Test
libraryDependencies += "com.h2database" % "h2"           % "1.4.200"