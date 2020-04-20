import java.net.URI
import java.util.*

plugins {
    java
    maven
    jacoco
    id("com.github.hierynomus.license") version "0.15.0"
    id("com.github.gradle-git-version-calculator") version "1.1.0"
    id("io.franzbecker.gradle-lombok") version "3.1.0"
    id("org.sonarqube") version "2.7.1"
}

group = "com.github.1c-syntax"
version = gitVersionCalculator.calculateVersion("v")

repositories {
    mavenCentral()
    maven { url = URI("https://jitpack.io") }
}

val junitVersion = "5.5.2"
dependencies {

    // https://mvnrepository.com/artifact/io.vavr/vavr
    implementation("io.vavr", "vavr", "0.10.2")
    implementation("org.apache.commons", "commons-collections4", "4.4")

    implementation("com.fasterxml.jackson.dataformat", "jackson-dataformat-xml", "2.10.3")
    implementation("com.fasterxml.jackson.module", "jackson-module-parameter-names", "2.10.3")
    // логирование
    implementation("org.slf4j", "slf4j-api", "1.8.0-beta4")
    implementation("org.slf4j", "slf4j-simple", "1.8.0-beta4")
    // прочее
    implementation("commons-io", "commons-io", "2.6")
    implementation("org.apache.commons", "commons-lang3", "3.9")
    implementation("com.github.1c-syntax", "utils", "0.2.1")
    // генерики
    compileOnly("org.projectlombok", "lombok", lombok.version)
    // тестирование
    testImplementation("org.junit.jupiter", "junit-jupiter-api", junitVersion)
    testRuntimeOnly("org.junit.jupiter", "junit-jupiter-engine", junitVersion)
    testImplementation("org.assertj", "assertj-core", "3.12.2")
    testImplementation("com.ginsberg", "junit5-system-exit", "1.0.0")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

sourceSets {
    main {
        resources {
            exclude("**/*.xsd")
        }
    }
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
    options.compilerArgs.add("-parameters")
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

license {
    header = rootProject.file("license/HEADER.txt")
    ext["year"] = "2019 - " + Calendar.getInstance().get(Calendar.YEAR)
    ext["name"] = "Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com>"
    ext["project"] = "MDClasses"
    strictCheck = true
    mapping("java", "SLASHSTAR_STYLE")
    excludes(listOf(
            "**/edt*/**",
            "**/origin*/**",
            "**/*.bin",
            "**/*.html",
            "**/*.properties",
            "**/*.xml",
            "**/*.json",
            "**/*.os",
            "**/*.bsl"))
}

tasks.register("precommit") {
    description = "Run all precommit tasks"
    group = "Developer tools"
    dependsOn(":test")
    dependsOn(":licenseFormat")
}
