# Publishing ink-basic to Maven Central

`ink-basic` publishes to Maven Central as `com.cesartista.canvas:ink-basic`.
This is what makes the design system **resolvable** — a consumer just adds
`implementation("com.cesartista.canvas:ink-basic:0.1.0")` and Gradle pulls it
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

> ⚠️ **`~/.gradle/gradle.properties`, not the one in this repo.** The repo has
> its own `gradle.properties` holding build settings, and it is **tracked by
> git** — same filename, different directory. Credentials pasted into the repo
> copy are one `git commit` away from being published. If that happens, treat
> it as an exposure and **rotate** (revoke the Central token, regenerate the
> GPG key); deleting the line is not sufficient once it is in history.

### 1. Create a Central Portal account + namespace

1. Sign up at https://central.sonatype.com (Google/GitHub or an account).
   Use the **same email** that owns the namespace you'll claim.
2. Register the **`com.cesartista.canvas`** namespace. Central grants a
   namespace only to whoever controls the matching identity — and because
   `com.cesartista.*` is a **reverse-DNS domain** coordinate (not a code-host
   one), the identity Central checks is the **`cesartista.com` domain**, not a
   GitHub account:
   - https://central.sonatype.com/publishing/namespaces → *Add namespace*
   - Enter `com.cesartista.canvas`. Registering the parent `com.cesartista`
     instead also works and covers every `com.cesartista.*` child, so you never
     have to re-verify for a future sibling artifact.
   - The portal issues a verification code. Publish it as a **DNS `TXT` record
     on `cesartista.com`**, then hit *Verify*. Follow whatever the portal
     actually shows — it is the authority on the current flow — and wait for
     approval.

> **Prerequisite:** this namespace requires you to own and control DNS for
> `cesartista.com`. If you do not, Central will not approve it; the fallback
> that needs no domain is a code-host namespace such as
> `io.github.<your-github-user>`, which is verified by creating a repo the
> portal names. Changing the group later means republishing under new
> coordinates, so settle this **before** the first release.

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
signingInMemoryKeyId=<SHORT_KEY_ID>
signingInMemoryKeyPassword=<gpg-passphrase>
```

> **Use the SHORT (8-hex) key id here** — the last 8 characters of the long id.
> Gradle's in-memory signing looks the key up by short id; giving it the 16-hex
> long form fails with a misleading `Could not read PGP secret key`, which reads
> like a corrupt key but is really "no key matched that id". Omitting the
> property entirely also works when the keyring holds a single key.
>
> Two things that are **not** problems, despite looking like them: the value
> must keep its `-----BEGIN PGP PRIVATE KEY BLOCK-----` / `-----END ...` lines,
> and the `\n\n` after the header is correct — the blank line separating armor
> headers from the payload is part of the format.
>
> Verify signing before you go near Central:
>
> ```bash
> ./gradlew clean :ink-basic:publishToMavenLocal
> ls ~/.m2/repository/com/cesartista/canvas/ink-basic/0.1.0/*.asc   # expect 5
> gpg --verify <file>.asc <file>                                    # "Good signature"
> ```

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
