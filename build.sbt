lazy val scalaV = "2.12.1"
lazy val akkaV = "2.4.14"
lazy val fsmV = "1.1.0"


lazy val commonsSettings = Seq(
  organization := "cl.tastets.life",
  version := fsmV,
  scalaVersion := scalaV
)

lazy val root = (project in file("."))
  .settings(commonsSettings: _*)
  .settings(
    name := "fsm",
    libraryDependencies ++= dependencies,
    credentials += Credentials(Path.userHome / "archiva.credentials"),
    resolvers += "Local Maven Repository" at "file:///" + Path.userHome + "/.m2/repository",
    resolvers += "Tastets Maven Repository" at "http://maven.gps.cl:8080/archiva/repository/tastets/",
    publishTo := Some("Tastets Maven Repository" at "http://maven.gps.cl:8080/archiva/repository/tastets/"),
    artifact in (Compile, assembly) := {
      val art = (artifact in (Compile, assembly)).value
      art.copy(`classifier` = Some("assembly"))
    }
  )
  .settings(addArtifact(artifact in(Compile, assembly), assembly).settings: _*)
  .settings(assemblyJarName in assembly := s"fsm-assembly-$fsmV.jar")

/* dependencies */
lazy val dependencies = Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaV,
  "com.typesafe.akka" %% "akka-testkit" % akkaV,
  "org.scalatest" %% "scalatest" % "2.2.6" % Test,
  "junit" % "junit" % "4.10" % Test
)

assemblyMergeStrategy in assembly := {
  case PathList(ps @ _*) if ps.last endsWith ".class" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".xsd" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".xjd" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".xjb" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".properties" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".handlers" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".fixml" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".txt" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".factory" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".map" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".xml" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith "mailcap" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".html" => MergeStrategy.first
  case PathList("META-INF", "io.netty.versions.properties") => MergeStrategy.last
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}

test in assembly := {}