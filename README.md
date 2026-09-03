# ink-basic

**Android / Jetpack Compose** implementation of the agnostic CANVAS UI contract
defined in the sibling **Palette** repo. This is the default, swappable "look" —
not a design system lock-in; you can delete or replace it and drop in another
palette without touching components.

## Install

Published to Maven Central:

```kotlin
// app/build.gradle.kts
dependencies {
    implementation("com.cesartista.canvas:ink-basic:0.1.0")
}
```

Full consumption options (including the local composite-build path for offline
or pre-release work) are in [CONSUMING.md](CONSUMING.md); release mechanics are
in [PUBLISHING.md](PUBLISHING.md).

## Relationship to the other repos
- **CANVAS** — overall agentic coding system; owns the *non-themeable* core
  correctness floor (a11y, 48dp touch, contrast).
- **Palette** — agnostic, host-neutral design contract: token levels + component
  inventory (`docs/0001-ui-token-contract.md`, `docs/0002-component-inventory.md`).
- **ink-basic** (this repo) — the concrete Compose/Android realization. Swappable.

## Token contract (mirrors Palette ADR-0001)
Four tiers:
- **T1 Primitives** — raw color/type/space values.
- **T2 Alias/M3 bridge** — maps T3 semantics onto Compose/M3 under the hood.
- **T3 Semantics — THE contract.** Components consume only these.
- **T4 Component sugar** — derived shorthand; never invents new values.

Strict completeness: a palette is only valid when **light + dark + highContrast**
all resolve. Components never read primitives, hex, or raw `Dp` directly — only
T3 semantic tokens via `LocalSemanticTokens`.

## Layout
```
ink-basic/src/main/kotlin/com/canvas/ink/basic/
├── token/       T3 semantic token definitions (Color, Type, Space, Radius,
│                Elevation, Motion, Sizing, Border) + SemanticTokens aggregate
├── layout/      Screen structure (Palette ADR-0003): shells, ScreenState
│                phases, CanvasStateHost, CanvasCollection, CanvasSection,
│                CanvasListBody, CanvasFormBody
├── palette/     Palette (light/dark/highContrast), DefaultPalette,
│                CanvasTheme (LocalSemanticTokens + M3 bridge + typography)
└── component/   T3 tokens only — CanvasButton, CanvasButtonSecondary,
                 CanvasCard, CanvasTextField, CanvasListItem, CanvasTopBar,
                 CanvasBottomNav, CanvasTabRow, CanvasEmptyState,
                 CanvasErrorState,                  CanvasSnackbar, CanvasProgress, and the extended set:
                 CanvasAvatar, CanvasBadge,
                 CanvasIconButton, CanvasFAB, CanvasCheckbox, CanvasToggle,
                 CanvasRadioButton, CanvasChip, CanvasTag, CanvasTooltip,
                 CanvasDivider, CanvasSearchBar, CanvasSelect, CanvasSlider,
                 CanvasBottomSheet, CanvasDialog, CanvasToast, CanvasBanner,
                 CanvasSegmentedControl, TextFromType
```

### Notes
- **Spacing scale**: the seven canonical steps (4/8/12/16/24/32/48) plus
  extended sub-steps (2/6/20/40/80) (`SpaceTokens`).
- **Text scale** (10 styles, `TypeTokens`) is the richer ink-basic scale (see
  ADR-0001 Type).
- **Border tokens** (`border.thin=1/medium=2/thick=4`) are specified in ADR-0001
  and enforced by `PaletteTest`.

## Consuming
```kotlin
CanvasTheme {           // or CanvasTheme(palette = myPalette)
    // Canvas components read LocalSemanticTokens automatically
    CanvasButton("Go", onClick = {})
    CanvasCard { CanvasEmptyState("No items") }
}
```

## Screen structure (ADR-0003)

Components alone do not make screens consistent. `layout/` supplies the frame and the phase
host so every screen gets the same regions, page inset and loading/empty/error handling
without writing them:

```kotlin
CanvasScreenScaffold(
    topBar = { CanvasTopBar(title = "Products") },
) { padding ->
    CanvasStateHost(state = state, onRetry = onRefresh) { products ->
        CanvasListBody(
            items = products,
            key = { it.id },          // required — an unkeyed list is a perf defect
            contentPadding = padding,
        ) { ProductRow(it) }
    }
}
```

`ScreenState` is a sum type — `Loading` · `Empty(reason)` · `Error(message)` · `Content(value)`
— so "loading and error at once" cannot be represented. Map your UI state onto it in the
state holder; the screen never decides a phase.

### Shells

A screen's frame is one of three, chosen **independently of its archetype**:

| Shell | Chrome | Bottom |
|---|---|---|
| `CanvasPageShell` | branded top bar | footer **or** tab bar |
| `CanvasOverlayShell` | dismiss affordance | optional pinned action, never a footer |
| `CanvasFocusedShell` | none | optional pinned action |

Each declares a `NavigationModel`. Drawer versus bottom tabs is a *different navigation
graph*, not a different rendering, so it is stated rather than assumed — and switching an ink
across models is a decision for the app, not something an ink does silently.

> **`CanvasScreenScaffold` is superseded by `CanvasPageShell`.** It predates the shell concept
> and cannot declare a navigation model. It remains only because it is published API with
> existing callers; removing it is a breaking change owed a proper deprecation cycle. It is a
> **migration in progress, not a choice** — new screens use the shells. See its KDoc for the
> planned three-step removal.

### Slots, not widgets

`CanvasCollection` is the first archetype expressed as slots. The app supplies
`CollectionItemSlots` — title, supporting, price, priceCompare, discountLabel, rating — as
*values*, and the ink decides typography, arrangement and density:

```kotlin
CanvasCollection(
    items = products,
    key = { it.id },
    slots = { CollectionItemSlots(title = it.title, price = it.displayPrice) },
    media = { p, inkModifier -> AsyncImage(p.imageUrl, null, inkModifier) },
    density = CollectionDensity.Grid2,
)
```

Note `media`: the app passes the lambda, **the ink passes the `Modifier` into it**. The app
owns *which* image; the ink owns *how large and what shape*. Size or clip media at the call
site and the archetype becomes decorative — a second ink could no longer restructure it.

## Component contract
Components are immutable toward tokens — they read what `LocalSemanticTokens`
provides and never invent values. Palettes are swappable; components are not
themeable at the source level (per the split locked with CANVAS: core correctness
stays in CANVAS, looks live here).
