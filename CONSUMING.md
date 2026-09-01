# Consuming ink-basic

`ink-basic` is an **Android library** (`:ink-basic`) exposing the swappable
Compose component set + `CanvasTheme`. It is meant to be pulled into an
Android app project as a dependency — not copied.

Two supported pathways (no publishing infra required):

## Option A — Composite build via Git submodule (recommended, offline)

Add this repo as a submodule and register it as a composite build, so
`:ink-basic` resolves from source alongside your project.

```bash
# from your app repo root
git submodule add <ink-basic-repo-url> vendor/ink-basic
```

**1. Give the library a coordinate.** Composite builds substitute by
`group:name`, so this is required, not optional — without it Gradle has nothing
to match against. In `ink-basic/build.gradle.kts` *inside this repo*:

```kotlin
group = "com.canvas"
version = "0.1.0"

android { namespace = "com.canvas.ink.basic" }
```

**2. Include the build.** In your **root** `settings.gradle.kts`:

```kotlin
rootProject.name = "my-app"
include(":app")

includeBuild("vendor/ink-basic")
```

With `group` and `version` set, Gradle substitutes `com.canvas:ink-basic`
for the included build's `:ink-basic` module automatically — no explicit
mapping needed.

**3. (Only if automatic substitution misses.)** Declare it explicitly. Note
this belongs **inside the `includeBuild` block** — `dependencySubstitution` is
not a member of `dependencyResolutionManagement`, and putting it there fails to
evaluate:

```kotlin
includeBuild("vendor/ink-basic") {
    dependencySubstitution {
        substitute(module("com.canvas:ink-basic")).using(project(":ink-basic"))
    }
}
```

**4. Depend on the coordinate.** In `app/build.gradle.kts`:

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
> (see `settings.gradle.kts`).

### Compose dependencies come with it

`ink-basic` exposes Compose types in its public API (`Modifier`, `Color`, `Dp`,
`TextStyle`, `Easing`), so those artifacts are declared `api(...)` and the
Compose BOM is exported as an `api(platform(...))` constraint. You do **not**
need to re-declare them to call `CanvasButton`, and your Compose versions are
aligned to the same BOM — override the BOM in your own build if you need a
different one.

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
