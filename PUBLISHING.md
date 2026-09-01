# Publishing ink-basic to Maven Central

`ink-basic` publishes to Maven Central as `io.github.dcesartista:ink-basic`.
This is what makes the design system **resolvable** — a consumer just adds
`implementation("io.github.dcesartista:ink-basic:0.1.0")` and Gradle pulls it
from `mavenCentral()` (a repo every Android build already has). No vendored
copies, no submodules, no drift.

Publishing uses the `com.vanniktech.maven.publish` plugin against the Central
Portal (new API). The build file is already configured — coordinates, POM,
signing hook, and Central target are in `ink-basic/build.gradle.kts`.

---

## One-time setup (do this once, per machine)

The following credentials/signing are read from Gradle properties or env vars,
**never committed**. Put them in `~/.gradle/gradle.properties` (or export as
env vars in CI).

### 1. Create a Central Portal account + namespace

1. Sign up at https://central.sonatype.com (Google/GitHub or an account).
   Use the **same email** that owns the namespace you'll claim.
2. Register the **`io.github.dcesartista`** namespace, proving you control the
   `dcesartista` GitHub account:
   - https://central.sonatype.com/publishing/namespaces → *Add namespace*
   - `io.github.dcesartista` → it will ask you to create a public repo named
     `central-portal-io-github-dcesartista` in the dcesartista org, or use the
     verify-file flow. Follow the portal's instructions and wait for approval.

### 2. Create a user token

- https://central.sonatype.com/account → *Generate User Token*.
- You get a `username`/`password` pair. These are your publish credentials
  (distinct from your login). Store them in `~/.gradle/gradle.properties`:

```properties
mavenCentralUsername=<user-token-username>
mavenCentralPassword=<user-token-password>
```

### 3. Create a GPG key and register its public key

Maven Central requires signed artifacts. The plugin signs via an in-memory
key. Generate a key (ids must be 8–16 hex chars):

```bash
gpg --gen-key            # use name/email; remember the passphrase
gpg --list-secret-keys   # note the KEY ID (e.g. ABC123DEF4567890)
gpg --keyserver keyserver.ubuntu.com --send-keys <KEY_ID>   # distribute public key
```

Export the private key **ASCII-armored** (prints to stdout — keep it safe):

```bash
gpg --export-secret-keys --armor <KEY_ID>
```

Paste the full `-----BEGIN PGP PRIVATE KEY BLOCK-----` … `-----END …-----`
string into a `signingKey` property (single line, `\n` for newlines) or an env
var, plus the key password:

```properties
signingInMemoryKey=<ascii-armored-private-key-with-\n>
signingInMemoryKeyId=<KEY_ID>
signingInMemoryKeyPassword=<gpg-passphrase>
```

> The build signs **only when** one of `signingInMemoryKey`, `signing.keyId`,
> or `signing.secretKeyRingFile` is present, so `publishToMavenLocal` still
> works before you've done this setup.

---

## Publishing a release

With credentials + signing configured:

```bash
# Manual release: upload to Central, then click Publish on the deployment
# at https://central.sonatype.com/publishing/deployments
./gradlew publishToMavenCentral

# Fully automatic release: upload + validate + publish
./gradlew publishAndReleaseToMavenCentral
```

Central can take 10–30 minutes to make artifacts visible.

### Local / quick verification (no Central needed)

```bash
./gradlew publishToMavenLocal
```

This publishes into `~/.m2/repository` so you can verify the coordinate
resolves locally — same artifact shapes, no account or signing required.
To consume from `mavenLocal()` in an app, add:

```kotlin
dependencyResolutionManagement { repositories { mavenLocal(); google(); mavenCentral() } }
```

### Version bumps

Update version in `ink-basic/build.gradle.kts`
(`mavenPublishing { coordinates(...) }`). Maven Central is **immutable** —
never re-publish the same `group:artifact:version`; bump the version.

## Secrets summary (never commit)

| Property (in `~/.gradle/gradle.properties` or env) | Where from |
| --- | --- |
| `mavenCentralUsername` / `mavenCentralPassword` | Central Portal user token |
| `signingInMemoryKey` / `signingInMemoryKeyId` / `signingInMemoryKeyPassword` | your GPG key |
