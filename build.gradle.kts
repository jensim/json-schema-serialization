plugins {
  kotlin("multiplatform") version "1.7.0"
  kotlin("plugin.serialization") version "1.7.0"
  id("org.jetbrains.dokka") version "1.7.0"
  `maven-publish`
}

repositories {
  mavenCentral()
}

kotlin {
  jvm {
    compilations.all {
      kotlinOptions.jvmTarget = "11"
    }

    testRuns.all {
      executionTask {
        useJUnitPlatform()
      }
    }
  }
/*
  js(BOTH) {
    nodejs {
      testTask {
        with(compilation) {
          kotlinOptions {
            moduleKind = "commonjs"
          }
        }
      }
    }
  }
*/

  mingwX64()
  linuxX64()
  macosX64()

  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation(kotlin("reflect"))
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
      }
    }

    val commonTest by getting {
      dependencies {
        implementation(kotlin("test-common"))
        implementation(kotlin("test-annotations-common"))
      }
    }

    val jvmMain by getting
    val jvmTest by getting {
      dependencies {
        implementation(kotlin("test-junit5"))
        runtimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
      }
    }

  }
  tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
      freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
      freeCompilerArgs += "-opt-in=kotlinx.serialization.ImplicitReflectionSerializer"
      freeCompilerArgs += "-opt-in=kotlinx.serialization.ExperimentalSerializationApi"
    }
  }
}

publishing {
  repositories {
    /*
    maven {
      name = "OSSRH"
      url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
      credentials {
        username = System.getenv("MAVEN_USERNAME")
        password = System.getenv("MAVEN_PASSWORD")
      }
    }
    */
    maven {
      name = "GitHubPackages"
      url = uri("https://maven.pkg.github.com/octocat/hello-world")
      credentials {
        username = System.getenv("GITHUB_ACTOR")
        password = System.getenv("GITHUB_TOKEN")
      }
    }
  }
}
