# ui-default

**Android / Jetpack Compose** implementation of the agnostic CANVAS UI contract
defined in the sibling **Palette** repo. This is the default, swappable "look" —
not a design system lock-in; you can delete or replace it and drop in another
palette without touching components.

## Relationship to the other repos
- **CANVAS** — overall agentic coding system; owns the *non-themeable* core
  correctness floor (a11y, 48dp touch, contrast).
- **Palette** — agnostic, host-neutral design contract: token levels + component
  inventory (`docs/0001-ui-token-contract.md`, `docs/0002-component-inventory.md`).
- **ui-default** (this repo) — the concrete Compose/Android realization. Swappable.

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
uidefault/src/main/kotlin/com/canvas/uidefault/
├── token/       T3 semantic token definitions (Color, Type, Space, Radius,
│                Elevation, Motion, Sizing) + SemanticTokens aggregate
├── palette/     Palette (light/dark/highContrast), DefaultPalette,
│                CanvasTheme (LocalSemanticTokens + M3 bridge + typography)
└── component/   T3 tokens only — CanvasButton, CanvasCard, CanvasTextField,
                 CanvasListItem, CanvasTopBar, CanvasBottomNav, CanvasTabRow,
                 CanvasEmptyState, CanvasErrorState, CanvasSnackbar,
                 CanvasProgress, TextFromType
```

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
