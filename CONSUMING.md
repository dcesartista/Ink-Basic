# Consuming ink-basic

`ink-basic` is an **Android library** (`:ink-basic`) exposing the swappable
Compose component set + `CanvasTheme`. It is meant to be pulled into an
Android app project as a dependency — not copied.

Two supported pathways (no publishing infra required):

## Option A — Composite build via Git submodule (recommended, offline)

Add this repo as a submodule inside your project and register it as a composite
build, so `:ink-basic` is resolved from source with your project.

```bash
# from your app repo root
git submodule add <ink-basic-repo-url> vendor/ink-basic
```

Then declare the export coordinate in `ink-basic/build.gradle.kts` (it already
has `namespace`; add the artifact coordinate):

```kotlin
// ink-basic/build.gradle.kts
android { namespace = "com.canvas.ink.basic" }

// optional: give the library a stable coordinate for substitution
// group = "com.canvas"; version = "0.1.0"
```

In your **root** `settings.gradle.kts`:

```kotlin
rootProject.name = "my-app"
include(":app")

includeBuild("vendor/ink-basic")

// Map the published coordinate to the included source module (offline).
dependencyResolutionManagement {
    dependencySubstitution {
        substitute(module("com.canvas:ink-basic"))
            .using(project(":ink-basic")) // the module inside ink-basic
    }
}
```

Then in `app/build.gradle.kts` just depend on the coordinate — Gradle swaps it
to your local source automatically:

```kotlin
dependencies {
    implementation("com.canvas:ink-basic:0.1.0")
}
```

Wrap your app:

```kotlin
setContent {
    CanvasTheme { // from com.canvas.ink.basic.palette
        AppNavHost()
    }
}
```

> **Module name.** The library module inside this repo is `:ink-basic`
> (see `settings.gradle.kts`). The `substitute(...using(project(":ink-basic")))`
> line resolves `:ink-basic` within the composite build.

## Option B — Publish to a local/remote Maven

`ink-basic/build.gradle.kts` is a standard `com.android.library`. Add a
`maven-publish` block (or a `com.vanniktech`/`gradle-maven-publish)` plugin
alias) to publish `com.canvas:ink-basic` to your Maven repo, then consume
with:

```kotlin
implementation("com.canvas:ink-basic:<version>")
```

This is the path to take once you have a CI/registry. Until then, Option A is
sufficient and fully offline.

## Naming: `ink-basic` vs `android-*`
Per ADR-0002, components are named with the `Canvas` / `CanvasXxx` prefix but
kept **neutral and host-agnostic** (Button, TextField, Card, … in the source, a
generic `com.canvas.ink.basic` namespace). The **Android/Compose** realization
lives here; a Flutter or RN app consumes the same Palette contract
(agnostic) with its own implementation.
