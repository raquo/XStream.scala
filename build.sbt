enablePlugins(ScalaJSPlugin)

enablePlugins(ScalaJSBundlerPlugin)

npmDependencies in Compile ++= Seq(
  "xstream" -> "9.3.0"
)

scalaJSModuleKind := ModuleKind.CommonJSModule

emitSourceMaps in fastOptJS := false
