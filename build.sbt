scalaVersion       := "2.12.18"
Test / scalaSource := baseDirectory.value / "src" / "test"

libraryDependencies ++= Seq(
  "com.typesafe"   % "config"    % "1.4.3"
, "org.scalatest" %% "scalatest" % "3.2.17" % Test
)
