import com.adarshr.gradle.testlogger.theme.ThemeType
import com.epages.restdocs.apispec.gradle.OpenApi3Extension

val snippetsDir: String by extra("build/generated-snippets")

plugins {
  java
  id("org.springframework.boot") version "2.2.0.RELEASE"
  id("io.spring.dependency-management") version "1.0.8.RELEASE"
  id("org.asciidoctor.convert") version "1.5.8"
  id("com.adarshr.test-logger") version "1.7.0"
}

group = "com.nhn"
version = "1.0-SNAPSHOT"

buildscript {
  repositories {
    jcenter()
  }

  dependencies {
    classpath(group = "com.epages", name = "restdocs-api-spec-gradle-plugin", version = "0.9.6")
  }
}

apply(plugin = "com.epages.restdocs-api-spec")
apply(from = "openapi3.gradle")

repositories {
  mavenCentral()
  maven("https://repo.spring.io/milestone")
  maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
//  implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
//  implementation("org.springframework.boot.experimental:spring-boot-starter-data-r2dbc")
//  implementation("io.r2dbc:r2dbc-client:1.0.0.M7")
//  implementation("org.springframework.boot:spring-boot-starter-data-redis")
  implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")
  implementation("org.springframework.boot:spring-boot-starter-jdbc")
  implementation("org.springframework.boot:spring-boot-starter-rsocket")
//  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-webflux")
//  implementation("org.springframework.boot:spring-boot-starter-websocket")
//  implementation("org.apache.kafka:kafka-streams")
//  implementation("org.springframework.kafka:spring-kafka")
//  implementation("dev.miku:r2dbc-mysql")
  runtimeOnly("mysql:mysql-connector-java")
  testImplementation("org.springframework.boot:spring-boot-starter-test") {
    exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
  }
//  testImplementation("org.springframework.boot.experimental:spring-boot-test-autoconfigure-r2dbc")
  testImplementation("io.projectreactor:reactor-test")
//  testImplementation("org.springframework.kafka:spring-kafka-test")
  testImplementation("org.springframework.restdocs:spring-restdocs-webtestclient")

  testImplementation(group = "com.epages", name = "restdocs-api-spec-webtestclient", version = "0.9.5")
  implementation(group = "io.netty", name = "netty-codec-http", version = "4.1.43.Final")
  implementation(group = "io.projectreactor.netty", name = "reactor-netty", version = "0.9.0.RELEASE")
}

//dependencyManagement {
//  imports {
//    mavenBom("org.springframework.boot.experimental:spring-boot-bom-r2dbc:0.1.0.M2")
//  }
//}

tasks {
  test {
    outputs.dir(snippetsDir)
    useJUnitPlatform()

    testLogging {
      showStackTraces = false
    }

    jvmArgs = listOf("--enable-preview")
  }

  testlogger {
    theme = ThemeType.PLAIN
  }

  asciidoctor {
    inputs.dir(snippetsDir)
  }
}

//tasks.withType(JavaCompile::class.java) {
//  options.compilerArgs.add("--enable-preview")
//}

configure<JavaPluginConvention> {
  sourceCompatibility = JavaVersion.VERSION_1_10
}

configure<OpenApi3Extension> {
  setServer("http://localhost:8080")
  title = "Spring5.2 Reactive Webflux API"
  version = "1.0"
  description = """
                |Webflux API Document
                """.trimMargin()
  format = "yml"
  separatePublicApi = false
  outputFileNamePrefix = project.name
  outputDirectory = "${snippetsDir}/openapi3"
}

java {
  sourceCompatibility = JavaVersion.VERSION_1_10
  targetCompatibility = JavaVersion.VERSION_1_10
}
