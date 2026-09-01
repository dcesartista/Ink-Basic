// Root build file — plugins applied per-module (no application here; this is a library repo).
plugins {
    id("com.android.library") version "8.13.0" apply false
    id("org.jetbrains.kotlin.android") version "2.1.0" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.0" apply false
}
