[downloads]: https://github.com/SapphireMC/Sapphire/releases/
[wiki]: https://github.com/SapphireMC/Sapphire/wiki/

# Sapphire

Another purpur fork

[Downloads][downloads] - [wiki (coming soon)][wiki]

## License
[![GPL-3.0-only License](https://img.shields.io/github/license/SapphireMC/Sapphire?&logo=github)](LICENSE)

## Plugin development

- We recommend using [gradle](https://gradle.org) with kotlin dsl as the build system for your plugin.

### API

```kotlin
repositories {
    maven("https://the-planet.fun/repo/snapshots/")
    // Other repositories
}
```
```kotlin
dependencies {
    compileOnly("io.sapphiremc.sapphire:sapphire-api:1.19.4-R0.1-SNAPSHOT")
    // Other dependencies
}
```

- Also includes all API provided by
  [Purpur](https://github.com/PurpurMC/Purpur),
  [Pufferfish](https://github.com/pufferfish-gg/Pufferfish),
  [Paper](https://github.com/PaperMC/Paper),
  [Spigot](https://hub.spigotmc.org/stash/projects/SPIGOT/repos/spigot), and
  [Bukkit](https://hub.spigotmc.org/stash/projects/SPIGOT/repos/bukkit).

### Dev bundle

```kotlin
plugins {
    `java-library`
    id("io.papermc.paperweight.userdev") version "1.5.4"
    // Other plugins
}
```
```kotlin
repositories {
    maven("https://the-planet.fun/repo/snapshots")
    // Other repositories
}
```
```kotlin
dependencies {
    paperweight.devBundle("io.sapphiremc.sapphire", "1.19.4-R0.1-SNAPSHOT")
    // Other dependencies
}
```

- For more details see [this repo](https://github.com/PaperMC/paperweight-test-plugin)

## Setting up and building

### Initial setup
- Run `./gradlew applyPatches` in the root directory

### Creating a patch
- Patches are effectively just commits in either `Sapphire-API` or `Sapphire-Server`.
- To create one, just add a commit to either repo and run `./gradlew rebuildPatches`, and a patch will be placed in the `patches` folder.
- Modifying commits will also modify its corresponding patch file.

> See [CONTRIBUTING.md](CONTRIBUTING.md) for more detailed information.

### Compiling
- Use the command `./gradlew build` to build the API and server.
> Compiled JARs will be placed under `Sapphire-API/build/libs` and `Sapphire-Server/build/libs`.

- To get a Paperclip jar, run `./gradlew renamedReobfPaperclipJar`.
- To get a Bundler jar, run `./gradlew createReobfBundlerJar`
>Compiled JAR (Paperclip or Bundler) will be placed under `build/libs/`

- To install the `sapphire-api` and `dev-bundle` dependencies to your local Maven repo, run `./gradlew publishToMavenLocal`
