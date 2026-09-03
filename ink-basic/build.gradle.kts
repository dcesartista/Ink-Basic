import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.maven.publish)
}

android {
    namespace = "com.canvas.ink.basic"
    compileSdk = 36

    defaultConfig {
        minSdk = 34
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

// In-build identity matching the published Central coordinate, so a consumer
// can includeBuild() this checkout and substitute(module("com.cesartista.canvas:ink-basic"))
// against source (local dev) without changing its dependency line.
group = "com.cesartista.canvas"
version = "0.1.0"

dependencies {
    // `api` for everything this library exposes in its public signatures —
    // Modifier, Color, Dp, TextStyle, Easing — so consumers get them on their
    // compile classpath instead of having to re-declare them. The BOM is
    // exported too, aligning the consumer's Compose versions with ours.
    api(platform(libs.compose.bom))
    api(libs.compose.ui)
    api(libs.compose.ui.graphics)
    api(libs.compose.foundation)
    api(libs.compose.animation.core)

    // Internal only: nothing below appears in a public signature.
    implementation(libs.compose.material3)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.activity.compose)

    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)

    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.compose.ui.test.junit4)
}

mavenPublishing {
    // Version comes from the `version` property above — never a second literal.
    // A hardcoded version here silently overrides it, so a bump appears to work
    // while every publish keeps writing the old coordinate.
    coordinates(group.toString(), "ink-basic", version.toString())
    publishToMavenCentral(
        automaticRelease = providers.gradleProperty("mavenCentralAutomaticPublishing")
            .orElse("false").get().toBoolean(),
        validateDeployment = com.vanniktech.maven.publish.DeploymentValidation.VALIDATED,
    )
    // GPG signing is required by Maven Central. Sign only once a key is configured
    // (see PUBLISHING.md), so local/dry-run publishes keep working without one.
    val hasSigningKey: Boolean =
        providers.gradleProperty("signingInMemoryKey").orNull != null ||
            providers.gradleProperty("signing.keyId").orNull != null ||
            providers.gradleProperty("signing.secretKeyRingFile").orNull != null
    if (hasSigningKey) {
        signAllPublications()
    }
    pom {
        name.set("ink-basic")
        description.set(
            "Ink-basic — the first 'ink' in the ink series: Android/Jetpack Compose UI " +
                "component library that realizes the Palette design contract. Provides the " +
                "swappable default look (CanvasTheme, T3 semantic tokens, Canvas* components).",
        )
        inceptionYear.set("2026")
        url.set("https://github.com/dcesartista/Ink-Basic")
        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("repo")
            }
        }
        developers {
            developer {
                id.set("dcesartista")
                name.set("Dito Cesartista")
                url.set("https://github.com/dcesartista/")
            }
        }
        scm {
            url.set("https://github.com/dcesartista/Ink-Basic")
            connection.set("scm:git:git://github.com/dcesartista/Ink-Basic.git")
            developerConnection.set("scm:git:ssh://git@github.com/dcesartista/Ink-Basic.git")
        }
    }
}
