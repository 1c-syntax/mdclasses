import java.util.Calendar
import org.jreleaser.model.Active.*

plugins {
    `java-library`
    `maven-publish`
    jacoco
    id("cloud.rio.license") version "0.18.0"
    id("me.qoomon.git-versioning") version "6.4.4"
    id("io.freefair.lombok") version "9.5.0"
    id("io.freefair.javadoc-links") version "9.5.0"
    id("io.freefair.javadoc-utf-8") version "9.5.0"
    id("com.github.ben-manes.versions") version "0.61.0"
    id("ru.vyarus.pom") version "3.0.0"
    id("org.jreleaser") version "1.25.0"
    id("org.sonarqube") version "7.4.0.8496"
    id("me.champeau.jmh") version "0.7.3"
}

group = "io.github.1c-syntax"
gitVersioning.apply {
    refs {
        describeTagFirstParent = false
        tag("v(?<tagVersion>[0-9].*)") {
            version = "\${ref.tagVersion}\${dirty}"
        }

        branch("develop") {
            version = "\${describe.tag.version.major}." +
                    "\${describe.tag.version.minor.next}.0." +
                    "\${describe.distance}-SNAPSHOT\${dirty}"
        }

        branch(".+") {
            version = "\${ref}-\${commit.short}\${dirty}"
        }
    }

    rev {
        version = "\${commit.short}\${dirty}"
    }
}

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://central.sonatype.com/repository/maven-snapshots")
}

dependencies {

    implementation("org.apache.commons:commons-collections4:4.6.0")

    implementation("com.thoughtworks.xstream:xstream:1.4.21")

    // логирование
    implementation("org.slf4j:slf4j-api:2.0.18")

    // прочее
    implementation("commons-io:commons-io:2.22.0")

    implementation("io.github.1c-syntax:bsl-common-library:0.12.4")
    implementation("io.github.1c-syntax:utils:0.10.1")
    implementation("io.github.1c-syntax:supportconf:0.17.1") {
        exclude("io.github.1c-syntax", "bsl-common-library")
    }

    // быстрый поиск классов
    implementation("io.github.classgraph:classgraph:4.8.193")

    implementation("org.jspecify:jspecify:1.0.1")

    // тестирование
    testImplementation(platform("org.junit:junit-bom:6.1.3"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.assertj:assertj-core:3.27.7")
    testImplementation("com.ginsberg:junit5-system-exit:2.0.3")
    testImplementation("org.skyscreamer:jsonassert:1.5.3")
    testImplementation("org.objenesis:objenesis:3.6")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // логирование
    testImplementation("org.slf4j:slf4j-reload4j:2.0.18")

    // бенчмарк
    jmh("org.openjdk.jmh:jmh-core:1.37")
    jmhAnnotationProcessor("org.openjdk.jmh:jmh-generator-annprocess:1.37")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
    withSourcesJar()
    withJavadocJar()
}

jmh {
    warmupIterations = 3
    iterations = 5
    fork = 2
    resultFormat = "JSON"
    resultsFile = file("build/jmh-results.json")
    profilers = listOf(
        "com.github._1c_syntax.bsl.mdclasses.benchmark.MemoryProfiler"
    )
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
        xml.outputLocation.set(layout.buildDirectory.file("reports/jacoco/test/jacoco.xml"))
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
        property(
            "sonar.coverage.jacoco.xmlReportPaths",
            "${layout.buildDirectory.get()}/reports/jacoco/test/jacoco.xml"
        )
    }
}

artifacts {
    archives(tasks["jar"])
    archives(tasks["sourcesJar"])
    archives(tasks["javadocJar"])
}

license {
    header = rootProject.file("license/HEADER.txt")
    skipExistingHeaders = false
    strictCheck = true
    ext["year"] = "2019 - " + Calendar.getInstance().get(Calendar.YEAR)
    ext["name"] = "Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com>"
    ext["project"] = "MDClasses"
    mapping("java", "SLASHSTAR_STYLE")
    include("**/*.java")
}


publishing {
    repositories {
        maven {
            name = "staging"
            url = layout.buildDirectory.dir("staging-deploy").get().asFile.toURI()
        }
    }
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
                // Добавлено для Maven Central validation
                issueManagement {
                    system.set("GitHub Issues")
                    url.set("https://github.com/1c-syntax/mdclasses/issues")
                }
                // Добавлено для Maven Central validation
                ciManagement {
                    system.set("GitHub Actions")
                    url.set("https://github.com/1c-syntax/mdclasses/actions")
                }
            }
        }
    }
}

tasks.register("precommit") {
    description = "Run all precommit tasks"
    group = "Developer tools"
    dependsOn(":test")
    dependsOn(":licenseFormat")
}

jreleaser {
    signing {
        active = ALWAYS
        armored = true
    }
    deploy {
        maven {
            mavenCentral {
                create("release-deploy") {
                    active = RELEASE
                    url = "https://central.sonatype.com/api/v1/publisher"
                    stagingRepository("build/staging-deploy")
                }
            }
            nexus2 {
                create("snapshot-deploy") {
                    active = SNAPSHOT
                    snapshotUrl = "https://central.sonatype.com/repository/maven-snapshots/"
                    applyMavenCentralRules = true
                    snapshotSupported = true
                    closeRepository = true
                    releaseRepository = true
                    stagingRepository("build/staging-deploy")
                }
            }
        }
    }
}
