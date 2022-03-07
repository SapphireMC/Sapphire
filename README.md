[website]: https://denaryworld.ru
[downloads]: https://github.com/SapphireMC/Sapphire/releases/
[wiki]: https://github.com/SapphireMC/Sapphire/wiki/

# Sapphire

Paper fork that is used on our server.

[Our server][website] - [Downloads][downloads] - [wiki (coming soon)][wiki]

## License
[![GPL-3.0-only License](https://img.shields.io/github/license/SapphireMC/Sapphire?&logo=github)](LICENSE)

## API

### Dependency Information

#### Maven
```xml
<repository>
    <id>sapphiremc</id>
    <url>http://repo.denaryworld.ru/snapshots/</url>
</repository>
```
```xml
<depencency>
    <groupId>io.sapphiremc.sapphire</groupId>
    <!-- For API use sapphire-api, for Server use sapphire-server -->
    <artifactId>sapphire-api</artifactId>
    <version>1.18.2-R0.1-SNAPSHOT</version>
    <scope>provided</scope>
</depencency>
```

#### Gradle groovy
```groovy
repositories {
    maven {
        url 'http://repo.denaryworld.ru/snapshots/'
        allowInsecureProtocol = true
    }
}
```
```groovy
dependencies {
    // For API use sapphire-api, for Server use sapphire-server
    compileOnly 'io.sapphiremc.sapphire:sapphire-api:1.18.2-R0.1-SNAPSHOT'
}
```

#### Gradle kotlin
```kotlin
repositories {
    maven("http://repo.denaryworld.ru/snapshots/") {
        isAllowInsecureProtocol = true
    }
}
```
```kotlin
dependencies {
    // For API use sapphire-api, for Server use sapphire-server
    compileOnly("io.sapphiremc.sapphire:sapphire-api:1.18.2-R0.1-SNAPSHOT")
}
```

Yes, this also includes all API provided by Paper, Spigot, and Bukkit.

## Building and setting up

#### Initial setup
Run the following command in the root directory:

```shell
./gradlew applyPatches
```

#### Compiling

Use the command `./gradlew build` to build the API and server. Compiled JARs
will be placed under `sapphire-api/build/libs` and `sapphire-server/build/libs`.

To get a Paperclip jar, run `./gradlew createReobfPaperclipJar`.

To get a Bundled jar, run `./gradlew createReobfBundlerJar`

To install the `sapphire-api` and `sapphire-server` dependencies to your local Maven repo, run `./gradlew publishToMavenLocal`

