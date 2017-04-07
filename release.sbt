name := "XStream.scala"

normalizedName := "xstream"

organization := "com.raquo"

scalaVersion := "2.11.8"

crossScalaVersions := Seq("2.11.8", "2.12.1")

homepage := Some(url("https://github.com/raquo/XStream.scala"))

licenses += ("MIT", url("https://github.com/raquo/XStream.scala/blob/master/LICENSE.txt"))

scmInfo := Some(
  ScmInfo(
    url("https://github.com/raquo/XStream.scala"),
    "scm:git@github.com/raquo/XStream.scala.git"
  )
)

developers := List(
  Developer(
    id = "raquo",
    name = "Nikita Gazarov",
    email = "nikita@raquo.com",
    url = url("http://raquo.com")
  )
)

publishMavenStyle := true

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

releaseCrossBuild := true

pomIncludeRepository := { _ => false }

useGpg := true

releasePublishArtifactsAction := PgpKeys.publishSigned.value
