enablePlugins(ScalaJSPlugin)

enablePlugins(ScalaJSBundlerPlugin)

npmDependencies in Compile ++= Seq(
  "xstream" -> "10.9.0"
)

scalaJSModuleKind := ModuleKind.CommonJSModule

emitSourceMaps := false
