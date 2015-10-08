name := "riffel"

organization := "eu.unicredit"

scalaVersion := "2.11.7"

version := "0.0.1-SNAPSHOT"

scalacOptions ++= Seq("-feature","-language:reflectiveCalls")

libraryDependencies += "edu.berkeley.cs" %% "chisel" % "2.2.29"
