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

group = "com.github.1c-syntax"
version = gitVersionCalculator.calculateVersion("v")

repositories {
    mavenCentral()
    maven { url = URI("https://jitpack.io") }
}

dependencies {

    compile("javax.xml.bind:jaxb-api:2.3.0")
    compile("javax.activation:activation:1.1")
    compile("org.glassfish.jaxb:jaxb-runtime:2.3.0")

    compile("org.slf4j", "slf4j-api", "1.8.0-beta4")
    compile("org.slf4j", "slf4j-simple", "1.8.0-beta4")

    compile("commons-io", "commons-io", "2.6")
    compile("org.apache.commons", "commons-lang3", "3.9")

    testImplementation("org.junit.jupiter", "junit-jupiter-api", "5.5.0")
    testRuntime("org.junit.jupiter", "junit-jupiter-engine", "5.5.0")
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

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
    reports {
        html.isEnabled = true
    }
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
//            packageName = "com.github._1c_syntax.mdclasses.jabx.original"
//        }
//    }
//
//    extension = true
//    arguments = listOf("-verbose")
//    outputDir = file("${project.buildDir}/generated-sources/xsd2java")
//}