# Changelog

All notable changes to **ink-basic** are recorded here.
This project follows [Semantic Versioning](https://semver.org/).

## [Unreleased] — 0.2.0

### Added
- **`layout/` — the screen-structure layer** (Palette ADR-0003). Components alone did not
  produce structurally consistent screens: nine screens built from the existing inventory
  produced nine hand-rolled top regions, four loading treatments, four error treatments,
  three screens that rendered nothing when empty, and three page paddings — none of them
  `space.layout.page`. `CanvasErrorState` shipped, was exported, and was used zero times.
  - `ScreenState` — sum type over the four mutually-exclusive UI phases
    (`Loading` · `Empty(reason)` · `Error(message)` · `Content(value)`), so "loading and
    error at once" is unrepresentable.
  - `CanvasStateHost` — renders exactly one phase, with complete defaults for loading,
    empty and error.
  - `CanvasScreenScaffold` — owns the top/bottom/floating regions and resolves page inset
    to `space.layout.page`.
  - `CanvasSection` — titled block at `space.layout.section`, title carries a heading semantic.
  - `CanvasListBody` — lazy collection body; **item identity is a required parameter**, so an
    unkeyed list cannot be written by accident.
  - `CanvasFormBody` — scrolling field column that applies the keyboard inset.

- **Three shells** (Palette ADR-0003): `CanvasPageShell`, `CanvasOverlayShell`,
  `CanvasFocusedShell`, cut from one frame. `NavigationModel` makes drawer-vs-tabs an
  explicit declaration rather than an ink's silent choice, because it changes the app's
  navigation graph, not just its rendering.
- **`CanvasCollection`** — the first slot-driven archetype. The app supplies
  `CollectionItemSlots` (values, not widgets) and an optional media lambda; the ink decides
  typography, arrangement, and media geometry. Two densities ship: `Grid2` and `RowCompact`.
  The media lambda receives **the ink's modifier**, so the app owns which image and the ink
  owns how large and what shape.
- `SizingTokens.rowMedia` — leading media in a compact collection row.

### Changed
- Publishing version is now single-sourced from the module's `version` property.
  `mavenPublishing { coordinates(...) }` previously hardcoded `"0.1.0"`, silently overriding
  any bump — every publish wrote the old coordinate while appearing to succeed.

### Sample
- `CollectionArchetypeProof` renders two unrelated commercial UI kits — Open Fashion's
  editorial catalogue and Stylish's marketplace listing — from **one `CanvasCollection`
  call**, differing only in density and which optional slots are supplied. Verified on
  device, not just compiled.

### Testing
- Compose behaviour is now verified on the JVM via **Robolectric** rather than on a device,
  preserving the suite's existing "no instrumentation needed" property. 23 tests green.
