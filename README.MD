# AniRena-API
Java API for https://anirena.com/

Since this api parses webpages, it might break any time.

# Installation
The recommended way to use this library is to add the github repository as a project in your build.sbt and let your project depend on it.
Sbt builds the library from source and will apply all commits in the repository.
``` sbt
lazy val anirena = RootProject(uri("https://github.com/kaysubs/AniRena-API"))
dependsOn(anirena)
```
Of course you may also use a different build tool that support that feature or manually compile this library and its dependencies.

# Usage
All api calls are in the classes `AniRenaApi` and `AniRenaAuthApi`.

For more example usages you may have a look at the `de.kaysubs.tracker.anirena.examples` package.
