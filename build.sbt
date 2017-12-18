enablePlugins(ScalaJSPlugin)

enablePlugins(ScalaJSBundlerPlugin)

npmDependencies in Compile ++= Seq(
  "xstream" -> "10.9.0"
)

libraryDependencies ++= Seq(
  "org.scalatest" %%% "scalatest" % "3.0.4" % Test
)

scalaJSModuleKind := ModuleKind.CommonJSModule

emitSourceMaps := false
