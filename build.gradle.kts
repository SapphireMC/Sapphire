import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    java
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "7.1.2" apply false
    id("io.papermc.paperweight.patcher") version "1.3.5"
}

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/") {
        content { onlyForConfigurations("paperclip") }
    }
}

dependencies {
    remapper("net.fabricmc:tiny-remapper:0.8.1:fat")
    decompiler("net.minecraftforge:forgeflower:1.5.498.22")
    paperclip("io.papermc:paperclip:3.0.2")
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")

    group = project.group
    version = project.version

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }
}

subprojects {
    tasks {
        withType<JavaCompile> {
            options.encoding = Charsets.UTF_8.name()
            options.release.set(17)
        }
        withType<Javadoc> {
            options.encoding = Charsets.UTF_8.name()
        }
        withType<ProcessResources> {
            filteringCharset = Charsets.UTF_8.name()
        }
        withType<Test> {
            testLogging {
                showStackTraces = true
                exceptionFormat = TestExceptionFormat.FULL
                events(TestLogEvent.STANDARD_OUT)
            }
        }
    }

    repositories {
        mavenCentral()
        maven("https://papermc.io/repo/repository/maven-public/")
    }
}

paperweight {
    serverProject.set(project(":sapphire-server"))

    remapRepo.set("https://maven.fabricmc.net/")
    decompileRepo.set("https://files.minecraftforge.net/maven/")

    usePaperUpstream(providers.gradleProperty("paperRef")) {
        withPaperPatcher {
            apiPatchDir.set(layout.projectDirectory.dir("patches/api"))
            apiOutputDir.set(layout.projectDirectory.dir("sapphire-api"))

            serverPatchDir.set(layout.projectDirectory.dir("patches/server"))
            serverOutputDir.set(layout.projectDirectory.dir("sapphire-server"))
        }
    }
}

tasks.generateDevelopmentBundle {
    apiCoordinates.set("io.sapphiremc.sapphire:sapphire-api")
    mojangApiCoordinates.set("io.papermc.paper:paper-mojangapi")
    libraryRepositories.set(
        listOf(
            "https://repo.maven.apache.org/maven2/",
            "https://libraries.minecraft.net/",
            "https://papermc.io/repo/repository/maven-public/",
            "https://maven.quiltmc.org/repository/release/",
            "http://repo.denaryworld.ru/snapshots/",
        )
    )
}

val mcVersion: String by project
val projVersion = project.version
tasks.register<Copy>("renamedReobfPaperclipJar") {
    group = "sapphire"
    dependsOn(tasks.createReobfPaperclipJar)
    from("build/libs/sapphire-paperclip-$projVersion-reobf.jar")
    into("build/libs")

    rename {
        it.replace("paperclip-$projVersion-reobf", mcVersion)
    }
}

allprojects {
    // Publishing API:
    // ./gradlew :sapphire-api:publish[ToMavenLocal]
    publishing {
        repositories {
            maven {
                name = "SapphireMC"
                url = uri("http://repo.denaryworld.ru/snapshots/")
                isAllowInsecureProtocol = true
                credentials(PasswordCredentials::class)
            }
        }
    }
}

publishing {
    // Publishing dev bundle:
    // ./gradlew publishDevBundlePublicationTo(MavenLocal|MyRepoSnapshotsRepository) -PpublishDevBundle
    if (project.hasProperty("publishDevBundle")) {
        publications.create<MavenPublication>("devBundle") {
            artifact(tasks.generateDevelopmentBundle) {
                artifactId = "dev-bundle"
            }
        }
    }
}
