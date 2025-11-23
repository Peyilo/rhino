plugins {
    kotlin("jvm") version "2.2.20"
    id("com.vanniktech.maven.publish") version "0.35.0"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    api("org.mozilla:rhino:1.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(11)
}

// MavenCentral Publishing
val artifactId = "rhino"
val groupId = "io.github.peyilo"
val versionId = "1.0.2"
val descriptions = "A lightweight Java library that integrates Rhino JavaScript engine with Kotlin coroutines."

val authorName = "Peyilo"
val developerId= authorName

val gitRepoName = artifactId
val gitUri = "github.com/${authorName}"
val emails = "peyilo.me@gmail.com"

val license = "Mozilla Public License 2.0"
val licenseUrl = "https://www.mozilla.org/MPL/2.0/"
val year = "2025"

description = descriptions
group = groupId
version = versionId

mavenPublishing {
    if (project.hasProperty("enablePublishing")) {
        publishToMavenCentral()
        signAllPublications()

        if (!project.hasProperty("mavenCentralUsername")) {
            throw IllegalArgumentException("mavenCentralUsername is not set")
        } else if (!project.hasProperty("mavenCentralPassword")) {
            throw IllegalArgumentException("mavenCentralPassword is not set")
        } else if (!project.hasProperty("signing.keyId")) {
            throw IllegalArgumentException("signing.keyId is not set")
        } else if (!project.hasProperty("signing.password")) {
            throw IllegalArgumentException("signing.password is not set")
        }

        coordinates(groupId, artifactId, versionId)

        pom {
            name.set(artifactId)
            description.set(descriptions)
            inceptionYear.set(year)
            url.set("https://$gitUri/$gitRepoName/")
            licenses {
                license {
                    name.set(license)
                    url.set(licenseUrl)
                    distribution.set(licenseUrl)
                }
            }
            developers {
                developer {
                    id.set(developerId)
                    name.set(authorName)
                    email.set(emails)
                    url.set("https://$gitUri")
                }
            }
            scm {
                url.set(gitRepoName)
                connection.set("scm:git:git://$gitUri/$gitRepoName.git")
                developerConnection.set("scm:git:ssh://git@$gitUri/$gitRepoName.git")
            }
        }
    }
}