pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://papermc.io/repo/repository/maven-public/")
    }
}

rootProject.name = "sapphire"

include("sapphire-api", "sapphire-server")

// Uncomment to enable the test plugin module
//include(":test-plugin")
