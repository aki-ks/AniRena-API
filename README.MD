# AniRena-API
Java API for https://anirena.com/

Since this api parses webpages, it might break any time.

# Installation
One way to use this library is to inculude it through jitpack.
How to use it with your build tool is explained at [their page](https://jitpack.io/#aki-ks/AniRena-API).

If you're building with sbt, you may add this library as a remote project in your `build.sbt` and let your project depend on it.
Sbt builds the library from source and will apply all new commits to the repository.
``` sbt
lazy val anirena = RootProject(uri("https://github.com/kaysubs/AniRena-API"))
dependsOn(anirena)
```

# Usage
All api calls are in the classes `AniRenaApi` and `AniRenaAuthApi`.

For more example usages you may have a look at the `de.kaysubs.tracker.anirena.examples` package.
