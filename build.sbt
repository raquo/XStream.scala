enablePlugins(ScalaJSPlugin)

enablePlugins(ScalaJSBundlerPlugin)

name := "XStream.scala"

normalizedName := "xstream"

organization := "com.raquo"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.8"

crossScalaVersions := Seq("2.11.8", "2.12.1")

homepage := Some(url("https://github.com/raquo/xstream-scala"))

licenses += ("MIT", url("https://github.com/raquo/xstream-scala/blob/master/LICENSE.md"))

npmDependencies in Compile ++= Seq(
  "xstream" -> "9.3.0"
)

scalaJSModuleKind := ModuleKind.CommonJSModule

emitSourceMaps in fastOptJS := false
