dependencies {
    implementation(project(":sapphire-api"))
}

tasks.processResources {
    val apiVersion = rootProject.providers.gradleProperty("mcVersion").forUseAtConfigurationTime().get()
        .split(".", "-").take(2).joinToString(".")
    val props = mapOf(
        "version" to rootProject.version,
        "apiversion" to apiVersion,
    )
    inputs.properties(props)
    filesMatching("plugin.yml") {
        expand(props)
    }
}