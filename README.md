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

## Component contract
Components are immutable toward tokens — they read what `LocalSemanticTokens`
provides and never invent values. Palettes are swappable; components are not
themeable at the source level (per the split locked with CANVAS: core correctness
stays in CANVAS, looks live here).
