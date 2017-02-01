name := "Scala SBT Template"

version := "0.1.0"

scalaVersion := "2.12.1"

organization := "com.mycode"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"

mainClass := Some("com.mycode.App")

resolvers ++= Seq("snapshots"     at "http://oss.sonatype.org/content/repositories/snapshots",
                "releases"        at "http://oss.sonatype.org/content/repositories/releases"
                )
resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"
coverageEnabled := true
