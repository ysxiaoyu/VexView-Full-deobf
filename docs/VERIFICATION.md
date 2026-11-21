# Verification report

Everything below was produced on this machine, against the original `vexview-2.7.3.jar`
(SHA-256 `E94E99AFF20D56960B2E912FE28EC1DA039D4A5AFA2BB26A33F57AF1D6412A3E`)
and the jar built by `build-local.ps1`.

## Why two name spaces matter

The shipped jar refers to Minecraft members by **SRG** names (`func_71410_x`,
`field_71466_p`). The compile SDK in `libs/` uses **MCP** names. So:

* to check **references** (do they exist?) the rebuilt MCP-named classes are compared against the
  MCP SDK;
* to check **behaviour** (does it run the same?) both jars must run in the *same* name space, so a
  second SDK is generated with the inverse map (`tools/mcp2srg.map`):

```powershell
java -cp "tools/deobf-tools.jar;tools/lib/*" RemapTool remap `
     libs/minecraft-1.12.2-forge-mcp.jar build/sdk-srg.jar tools/mcp2srg.map
```

Then the original jar and the reobfuscated rebuilt jar both run on `build/sdk-srg.jar`.

## 1. Link-level integrity

`tools/src/LinkCheck.java` walks every member reference in a jar and resolves it against a real
class path — owner class loads, and a field/method with that name **and** descriptor exists on the
owner or one of its super types.

```
$ java -cp "tools/deobf-tools.jar;tools/lib/*" LinkCheck build/classes.jar <sdk> <mc-libs...>
methods=1451 unresolved=0  fields=712 unresolved=0

$ java -Dsrg2mcp=mcp_stable -cp ... LinkCheck vexview-2.7.3.jar <sdk> <mc-libs...>
methods=1444 unresolved=1  fields=735 unresolved=0
    UNRESOLVED M scala/actors/threadpool/Arrays.asList([Ljava/lang/Object;)Ljava/util/List;
```

The single unresolved reference in the original is the Scala class that Minecraft never ships —
i.e. a latent crash in the original mod. The reconstruction resolves it by using
`java.util.Arrays`.

## 2. Structural equivalence

| Property | Original | Rebuilt |
| --- | --- | --- |
| classes | 168 | 168 |
| mod classes (`lk.vexview.*`) | 143 | 143 |
| method references | 1 444 | 1 451 |
| field references | 735 | 712 |
| resources | 29 | 29 (byte-identical) |

Reference counts differ slightly because the rebuilt classes are compiled by javac from source, so
member references are qualified with the static type the source uses (the JVM resolves either form
by walking the hierarchy — `LinkCheck` proves both resolve).

## 3. Behaviour: `org.json`

`tools/src/JsonScript.java` runs a 121-step script (7 documents × parse/pretty-print/keys/names/
similar, mutation, arrays, `quote`, `stringToValue`, `numberToString`, XML/Cookie/HTTP/CDL,
`JSONPointer`, `JSONTokener`) against both loaders and writes a transcript.

```
original : transcript lines: 121
rebuilt  : transcript lines: 121
diff     : (empty)  → IDENTICAL transcripts
```

## 4. Behaviour: static methods

`tools/src/DiffTest.java` pairs methods **by descriptor** (unambiguous pairs only) and compares the
returned value or the thrown exception class.

```
compared=172 equal=172 skipped=0 problems=0
```

## 5. Behaviour: instance methods

`tools/src/InstDiff.java` instantiates both classes (no-arg or `(String)` constructor) and compares
every simple-signature getter.

```
instance compared=64 equal=64 problems=0
```

## Bugs this suite found (and that are fixed in this tree)

| Symptom | Root cause | Fix |
| --- | --- | --- |
| `{"k":"v"}` printed as `{"k":"v""k":"v"}`, arrays likewise | CFR dropped the `else` in `JSONObject.write` / `JSONArray.write`, so the single-entry fast path and the loop both ran | restored `else if` |
| `Tools.m2/m3` returned the wrong elements | CFR turned `!a && !b && …` into `!(a \|\| b \|\| … \|\| !last)` | rewrote the conjunction |

## Reproduce

```powershell
# 1. build
pwsh -File .\build-local.ps1

# 2. SRG-named SDK for behaviour tests
$jdk8 = 'C:\path\to\jdk8'
& "$jdk8\bin\java.exe" -cp "tools\deobf-tools.jar;tools\lib\*" RemapTool remap `
    libs\minecraft-1.12.2-forge-mcp.jar build\sdk-srg.jar tools\mcp2srg.map

# 3. link check (MCP name space)
& "$jdk8\bin\java.exe" -cp "tools\deobf-tools.jar;tools\lib\*" LinkCheck `
    build\classes.jar libs\minecraft-1.12.2-forge-mcp.jar libs\*.jar

# 4. behaviour tests (SRG name space)
& "$jdk8\bin\java.exe" -cp "tools\deobf-tools.jar;tools\lib\*" DiffTest `
    vexview-2.7.3.jar build\libs\vexview-2.7.3-deobf.jar build\sdk-srg.jar libs\*.jar
& "$jdk8\bin\java.exe" -cp "tools\deobf-tools.jar;tools\lib\*" InstDiff `
    vexview-2.7.3.jar build\libs\vexview-2.7.3-deobf.jar build\sdk-srg.jar build\sdk-srg.jar libs\*.jar
```

`DiffTest`/`InstDiff`/`JsonScript` read the class mapping from `work/deobf.map` and
`work/classmap2.txt`; adjust the paths (constants at the top of each file) when running from a
different checkout.
