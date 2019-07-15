import java.net.URI

plugins {
    java
    maven
    jacoco
    id("com.github.gradle-git-version-calculator") version "1.1.0"
    id("de.qaware.gradle.plugin.xsd2java") version "1.0.0"
    id("io.franzbecker.gradle-lombok") version "3.1.0"
    id("org.sonarqube") version "2.7.1"
}

group = "org.github._1c_syntax"
version = gitVersionCalculator.calculateVersion("v")

repositories {
    mavenCentral()
    maven { url = URI("https://jitpack.io") }
}

dependencies {
    testCompile("junit", "junit", "4.12")

    testImplementation("org.junit.jupiter", "junit-jupiter-api", "5.5.0-M1")
    testRuntime("org.junit.jupiter", "junit-jupiter-engine", "5.5.0-M1")
    testCompile("org.assertj", "assertj-core", "3.12.2")
    testImplementation("com.ginsberg", "junit5-system-exit", "1.0.0")

    compileOnly("org.projectlombok", "lombok", lombok.version)

}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-Xlint:unchecked")
}

sonarqube {
    properties {
        property("sonar.sourceEncoding", "UTF-8")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.organization", "1c-syntax")
        property("sonar.projectKey", "1c-syntax_mdclasses")
        property("sonar.projectName", "MDClasses")
        property("sonar.exclusions", "**/gen/**/*.*")
    }
}

lombok {
    version = "1.18.8"
    sha256 = "0396952823579b316a0fe85cbd871bbb3508143c2bcbd985dd7800e806cb24fc"
}

//xsd2java {
//    schemas {
//        create("OriginalConfiguration") {
//            schemaDirPath = file("src/main/resources/xsd/original").toPath()
//            packageName = "org.github._1c_syntax.mdclasses.jabx.original"
//        }
//    }
//
//    extension = true
//    arguments = listOf("-verbose")
//    outputDir = file("${project.buildDir}/generated-sources/xsd2java")
//}