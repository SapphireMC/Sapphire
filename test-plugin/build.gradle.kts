repositories {
    maven("https://libraries.minecraft.net")
    mavenCentral()
}

dependencies {
    implementation(project(":sapphire-api"))
   // implementation("io.papermc.paper:paper-mojangapi:${version}")
}

tasks.processResources {
    val apiVersion = rootProject.properties["mcVersion"].toString()
        .split(".", "-").take(2).joinToString(".")
    val props = mapOf(
        "version" to project.version,
        "apiVersion" to apiVersion,
    )
    inputs.properties(props)
    filesMatching("plugin.yml") {
        expand(props)
    }
}