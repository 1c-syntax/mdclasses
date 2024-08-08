import me.qoomon.gitversioning.commons.GitRefType
import java.util.*

plugins {
    `java-library`
    `maven-publish`
    jacoco
    signing
    id("org.cadixdev.licenser") version "0.6.1"
    id("me.qoomon.git-versioning") version "6.4.3"
    id("com.gorylenko.gradle-git-properties") version "2.4.2"
    id("io.freefair.lombok") version "8.7.1"
    id("io.freefair.javadoc-links") version "8.7.1"
    id("io.freefair.javadoc-utf-8") version "8.7.1"
    id("io.freefair.maven-central.validate-poms") version "8.7.1"
    id("ru.vyarus.pom") version "3.0.0"
    id("org.sonarqube") version "5.1.0.4882"
    id("io.codearte.nexus-staging") version "0.30.0"
}

group = "io.github.1c-syntax"
gitVersioning.apply {
    refs {
        considerTagsOnBranches = true
        tag("v(?<tagVersion>[0-9].*)") {
            version = "\${ref.tagVersion}\${dirty}"
        }
        branch(".+") {
            version = "\${ref}-\${commit.short}\${dirty}"
        }
    }

    rev {
        version = "\${commit.short}\${dirty}"
    }
}
val isSnapshot = gitVersioning.gitVersionDetails.refType != GitRefType.TAG

repositories {
    mavenLocal()
    mavenCentral()
    maven(url = "https://jitpack.io")
    maven(url = "https://s01.oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {

    implementation("org.apache.commons", "commons-collections4", "4.4")

    // https://mvnrepository.com/artifact/com.thoughtworks.xstream/xstream
    implementation("com.thoughtworks.xstream", "xstream", "1.4.20")

    // логирование
    implementation("org.slf4j", "slf4j-api", "2.1.0-alpha1")

    // прочее
    implementation("commons-io", "commons-io", "2.8.0")
    implementation("io.github.1c-syntax", "utils", "0.6.1")
    implementation("io.github.1c-syntax", "bsl-common-library", "0.7.0")
    implementation("io.github.1c-syntax", "supportconf", "0.14.0") {
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
    testImplementation("org.slf4j", "slf4j-log4j12", "2.1.0-alpha1")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
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

tasks.test {
    jvmArgs("--add-opens=java.base/java.util=ALL-UNNAMED")
}

tasks.javadoc {
    options {
        this as StandardJavadocDocletOptions
        noComment(false)
    }
}

sonar {
    properties {
        property("sonar.sourceEncoding", "UTF-8")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.organization", "1c-syntax")
        property("sonar.projectKey", "1c-syntax_mdclasses")
        property("sonar.projectName", "MDClasses")
        property("sonar.exclusions", "**/resources/**/*.*")
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

signing {
    val signingInMemoryKey: String? by project      // env.ORG_GRADLE_PROJECT_signingInMemoryKey
    val signingInMemoryPassword: String? by project // env.ORG_GRADLE_PROJECT_signingInMemoryPassword
    if (signingInMemoryKey != null) {
        useInMemoryPgpKeys(signingInMemoryKey, signingInMemoryPassword)
        sign(publishing.publications)
    }
}

publishing {
    repositories {
        maven {
            name = "sonatype"
            url = if (isSnapshot)
                uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
            else
                uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")

            val sonatypeUsername: String? by project
            val sonatypePassword: String? by project

            credentials {
                username = sonatypeUsername // ORG_GRADLE_PROJECT_sonatypeUsername
                password = sonatypePassword // ORG_GRADLE_PROJECT_sonatypePassword
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])

            if (isSnapshot && project.hasProperty("simplifyVersion")) {
                version = findProperty("git.ref.slug") as String + "-SNAPSHOT"
            }

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

nexusStaging {
    serverUrl = "https://s01.oss.sonatype.org/service/local/"
    stagingProfileId = "15bd88b4d17915" // ./gradlew getStagingProfile
}

tasks.register("precommit") {
    description = "Run all precommit tasks"
    group = "Developer tools"
    dependsOn(":test")
    dependsOn(":updateLicenses")
}
