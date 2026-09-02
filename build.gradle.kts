// Root build file — plugins applied per-module (no application here; this is a
// library repo). Versions come from gradle/libs.versions.toml only: declaring
// them here as literals too lets the two drift silently, which has bitten us.
plugins {
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.maven.publish) apply false
}
