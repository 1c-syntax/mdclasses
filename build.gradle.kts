import java.net.URI
import java.util.*

plugins {
    `java-library`
    `maven-publish`
    jacoco
    id("org.cadixdev.licenser") version "0.6.1"
    id("com.github.gradle-git-version-calculator") version "1.1.0"
    id("io.freefair.lombok") version "6.6.1"
    id("io.freefair.javadoc-links") version "6.6.3"
    id("io.freefair.javadoc-utf-8") version "6.6.1"
    id("org.sonarqube") version "3.5.0.2730"
}

group = "com.github.1c-syntax"
version = gitVersionCalculator.calculateVersion("v")

repositories {
    mavenLocal()
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {

    // https://mvnrepository.com/artifact/io.vavr/vavr
    implementation("io.vavr", "vavr", "0.10.2")

    implementation("org.apache.commons", "commons-collections4", "4.4")

    // https://mvnrepository.com/artifact/com.thoughtworks.xstream/xstream
    implementation("com.thoughtworks.xstream", "xstream", "1.4.19")

    // логирование
    implementation("org.slf4j", "slf4j-api", "1.7.30")

    // прочее
    implementation("commons-io", "commons-io", "2.8.0")
    implementation("org.apache.commons", "commons-lang3", "3.11")
    implementation("com.github.1c-syntax", "utils", "0.4.0")
    implementation("io.github.1c-syntax", "bsl-common-library", "develop-SNAPSHOT")
    implementation("io.github.1c-syntax", "supportconf", "0.1.1") {
        exclude("io.github.1c-syntax", "bsl-common-library")
    }

    // быстрый поиск классов
    implementation("io.github.classgraph", "classgraph", "4.8.147")

    // тестирование
    testImplementation("org.junit.jupiter", "junit-jupiter-api", "5.7.0")
    testImplementation("org.junit.jupiter", "junit-jupiter-engine", "5.7.0")
    testImplementation("org.junit.jupiter", "junit-jupiter-params", "5.7.0")
    testImplementation("org.assertj", "assertj-core", "3.18.1")
    testImplementation("com.ginsberg", "junit5-system-exit", "1.0.0")
    testImplementation("org.skyscreamer", "jsonassert", "1.5.0")
    testImplementation("org.objenesis", "objenesis", "3.2")

    // логирование
    // https://mvnrepository.com/artifact/org.slf4j/slf4j-log4j12
    testImplementation("org.slf4j", "slf4j-log4j12", "1.7.30")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
    withSourcesJar()
    withJavadocJar()
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
        html.required.set(true)
    }
}

tasks.check {
    dependsOn(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
        xml.outputLocation.set(File("$buildDir/reports/jacoco/test/jacoco.xml"))
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
        property("sonar.coverage.jacoco.xmlReportPaths", "$buildDir/reports/jacoco/test/jacoco.xml")
    }
}

artifacts {
    archives(tasks["jar"])
    archives(tasks["sourcesJar"])
    archives(tasks["javadocJar"])
}

license {
    header(rootProject.file("license/HEADER.txt"))
    newLine(false)
    ext["year"] = "2019 - " + Calendar.getInstance().get(Calendar.YEAR)
    ext["name"] = "Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com>"
    ext["project"] = "MDClasses"
    exclude("**/*.yml")
    exclude("**/*.bin")
    exclude("**/*.html")
    exclude("**/*.properties")
    exclude("**/*.xml")
    exclude("**/*.json")
    exclude("**/*.os")
    exclude("**/*.bsl")
    exclude("**/*.orig")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            pom {
                description.set("Metadata read/write library for Language 1C (BSL)")
                url.set("https://github.com/1c-syntax/mdclasses")
                licenses {
                    license {
                        name.set("GNU LGPL 3")
                        url.set("https://www.gnu.org/licenses/lgpl-3.0.txt")
                        distribution.set("repo")
                    }
                }
                developers {
                    developer {
                        id.set("otymko")
                        name.set("Oleg Tymko")
                        email.set("olegtymko@yandex.ru")
                        url.set("https://github.com/otymko")
                        organization.set("1c-syntax")
                        organizationUrl.set("https://github.com/1c-syntax")
                    }
                    developer {
                        id.set("theshadowco")
                        name.set("Valery Maximov")
                        email.set("maximovvalery@gmail.com")
                        url.set("https://github.com/theshadowco")
                        organization.set("1c-syntax")
                        organizationUrl.set("https://github.com/1c-syntax")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/1c-syntax/mdclasses.git")
                    developerConnection.set("scm:git:git@github.com:1c-syntax/mdclasses.git")
                    url.set("https://github.com/1c-syntax/mdclasses")
                }
            }
        }
    }
}

tasks.register("precommit") {
    description = "Run all precommit tasks"
    group = "Developer tools"
    dependsOn(":test")
    dependsOn(":updateLicenses")
}
