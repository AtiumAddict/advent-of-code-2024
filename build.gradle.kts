val kotlin_version: String by project
val kotest_version: String by project
val logback_version: String by project

plugins {
  kotlin("jvm") version "1.9.0"
  id("io.ktor.plugin") version "3.0.1"
}

group = "com.atiumaddict"

version = "0.0.1"

application {
  mainClass.set("io.ktor.server.netty.EngineMain")

  val isDevelopment: Boolean = project.ext.has("development")
  applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories { mavenCentral() }

dependencies {
  implementation("io.ktor:ktor-server-core-jvm")
  implementation("io.ktor:ktor-server-netty-jvm")
  implementation("ch.qos.logback:logback-classic:$logback_version")
  implementation("io.ktor:ktor-server-config-yaml-jvm")
  testImplementation("io.ktor:ktor-server-test-host-jvm")
  testImplementation("io.kotest:kotest-runner-junit5:$kotest_version") // Kotest test runner
  testImplementation("io.kotest:kotest-assertions-core:$kotest_version") // Kotest core assertions
}
