# VexView 2.7.3 — Deobfuscated Source

[![Minecraft](https://img.shields.io/badge/Minecraft-1.12.2-green.svg)](https://www.minecraft.net/)
[![Forge](https://img.shields.io/badge/Forge-14.23.5.2860-orange.svg)](https://files.minecraftforge.net/)
[![Build](https://img.shields.io/badge/build-javac%201.8%20%C2%B7%200%20errors-brightgreen.svg)](#build)
[![Classes](https://img.shields.io/badge/classes-168%20%2F%20168-blue.svg)](#verification)
[![Behaviour](https://img.shields.io/badge/behaviour%20diff-0%20mismatches-success.svg)](#verification)

A fully deobfuscated, compilable reconstruction of the **VexView 2.7.3** Minecraft Forge mod
(MC 1.12.2) — the client side of the VexView custom-GUI system.

The shipped jar is a single 800 KB archive whose code was heavily mangled: every class name,
method name and field name was replaced by garbage, every string literal was encrypted per call
site, and local-variable slots were deliberately reused for values of different types. This
repository restores it to readable, buildable, genuinely editable Java.

```text
168 classes  ·  0 compile errors  ·  614 KB of readable source (146 files)
678 encrypted strings recovered  ·  1 006 names restored / regenerated
build → vexview-2.7.3-deobf.jar (234 entries, reobfuscated to SRG, drop-in for mods/)
```

---

## Verification

The reconstruction was not just "it compiles" — it was diff-tested against the original binary.

| Check | Result |
| --- | --- |
| Link-level integrity (every MC member reference resolved against a real Forge 1.12.2 SDK) | rebuilt **1 451 refs / 0 unresolved** · original 1 444 / 1 (a missing Scala class, see below) |
| Class inventory | **168 / 168** (143 mod classes + 25 bundled `org.json`) |
| Resources (`assets/`, `mcmod.info`, `pack.mcmeta`) | byte-identical |
| `org.json` behaviour transcript (parse / mutate / write / XML / Cookie / HTTP / CDL) | **121 lines identical** |
| Static-method differential fuzz (same input → same output or same exception) | **172 / 172 equal** |
| Instance-method differential fuzz | **64 / 64 equal** |

The differential tests load the original jar and the rebuilt jar in two isolated class loaders
and run both against the same SDK, then compare results value by value. They caught three real
decompilation artefacts during the work, all fixed here:

* `JSONObject.write` and `JSONArray.write` lost an `else` — every object/array printed its last
  element twice;
* `Tools.m2/m3` lost the "none of the markers present" conjunction — the filter returned the
  wrong elements.

See [`docs/VERIFICATION.md`](docs/VERIFICATION.md) for the full run log and how to reproduce it.

---

## Build

### Option A — offline build (no network, verified on this machine)

```powershell
pwsh -File .\build-local.ps1 -JavaHome 'C:\path\to\jdk8'
# → build/libs/vexview-2.7.3-deobf.jar
```

Pipeline: `javac` → member names remapped from MCP back to **SRG** (what Forge 1.12.2 expects at
runtime) → classes and resources packed. The SDK it compiles against lives in `libs/`
(reconstructed Minecraft 1.12.2 + Forge with Forge's access transformers already applied).

### Option B — ForgeGradle (normal IDE workflow)

```powershell
gradle setupDevWorkspace
gradle build
```

'> Forge has retired the `1.12.2-14.23.5.2860-userdev.jar` artifact. `build.gradle` therefore points
> at the SDK shipped in `libs/` first; switch back to the official repository if your mirror still
> has userdev.
>
> `libs/` is ~48 MB. If you build with ForgeGradle and your mirror still serves userdev, you can drop
> it from the repository (`.gitignore` it) — option A then needs it, option B does not.'

---

## Layout

```
src/main/java/            all 168 classes (lk.vexview.* + bundled org.json)
src/main/resources/       original assets, mcmod.info, pack.mcmeta
libs/                     offline compile SDK (MC 1.12.2 + Forge, MCP names, AT applied)
tools/
  deobf-tools.jar         the whole deobfuscation + verification toolchain
  src/                    its sources (remapper, string decryptor, slot splitter, diff tests …)
  mcp2srg.map             MCP→SRG member map used for reobfuscation (32 405 entries)
build-local.ps1           offline build script
build.gradle              ForgeGradle build
deobf-mapping.tsv         1 006 rename records: obf name → original name → final name
docs/DEOBFUSCATION.md     how the deobfuscation works, step by step
docs/VERIFICATION.md      differential-test report
```

---

## How the deobfuscation was done (short version)

1. **Caller-keyed string encryption** — each literal is decrypted with a key made of the calling
   class and method name. The real decryptor bytecode was reused to build same-named call stubs, and
   an ASM `SourceInterpreter` dataflow pass located the actual argument of every call (not merely the
   nearest `LDC`): 678 sites recovered.
2. **Class names** — the jar's own `META-INF/fml_cache_*.json` still contained the 143 original
   class names, forming a bijection with the 143 obfuscated classes. Confirmed with package class
   counts, inheritance chains, factory/dispatch tables and recovered strings.
3. **Member names** — the obfuscator collapsed most methods and fields onto the single illegal
   identifier `pcdlk,pcdlona`. Renaming is inheritance-aware: external overrides keep their names,
   override chains share one new name, `@SubscribeEvent`/`@Mod.EventHandler` methods get their
   original names back from the FML cache, the rest become `m1…mN` / `f1…fN`.
4. **SRG → MCP** — 4 922 `func_*` / `field_*` references mapped to MCP names (stable_39).
5. **Slot splitting** — the worst part: one JVM local slot holds values of unrelated types, which
   makes every decompiler emit uncompilable or silently wrong Java. A dataflow pass splits such
   slots per type run and recomputes stack map frames (383 methods).
6. **InnerClasses / access transformers** — rebuilt, so nested types and Forge-widened members
   resolve again.
7. **Decompilation** — CFR 0.152 as the primary decompiler, Vineflower 1.11.1 for the classes CFR
   cannot structure, then targeted manual repair of what neither can express.

Full write-up: [`docs/DEOBFUSCATION.md`](docs/DEOBFUSCATION.md).

---

## Known differences from the original jar

| Where | Why |
| --- | --- |
| `lk/vexview/tools/Tools.java` | the original referenced `scala.actors.threadpool.Arrays`, a package Minecraft never ships (latent `NoClassDefFoundError`). Uses `java.util.Arrays` now — same `asList` semantics. |
| `lk/vexview/gui/core/GifDecoder$GifFrame.java` | was an anonymous inner class; rebuilt as a standalone class with the same fields and constructor. |
| `lk/vexview/announcement/Type.java` | was a mangled `enum`; rewritten as a real enum with the original ordinal order (WARN/NORMAL/DOWNLOAD), old field names kept as aliases. |
| `VexButton.m4`, `VexSlot.m4`, `VexGifImage.m1`, `AbstractElementCollection.m12/m13` | members that differed from a parent only in return type — legal in bytecode, illegal in Java. Renamed, call sites updated. |
| method / field names | the originals were destroyed by the obfuscator, so they are regenerated by rule. **Class names, event-handler names and all string constants are genuine.** |

---

## Credits & licensing

* Original mod **VexView 2.7.3** © **LK520**, **lona**, **PluginsCDTribe** — <https://www.vexview.top/>.
  This repository contains no new content of its own beyond the deobfuscation, the tooling and the
  documentation; every line of game logic belongs to the original authors.
* It is published for study, interoperability and maintenance of servers that already use the mod.
  If the original authors object to this reconstruction, open an issue and it will be taken down.
* No license is granted here over the original code. The tooling under `tools/src` is provided
  as-is for reuse.
* Thanks to the CFR, Vineflower and ASM projects — none of this would exist without them.

