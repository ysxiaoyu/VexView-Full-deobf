# Offline build for the deobfuscated VexView 2.7.3 source tree.
#
#   javac (JDK 8)  ->  reobf (MCP member names -> SRG member names)  ->  jar
#
# The result is a drop-in Forge 1.12.2 client mod jar.
# Requires: JDK 8 (JAVA_HOME or -JavaHome), PowerShell 7.
param(
    [string]$JavaHome = $env:JAVA_HOME,
    [string]$Version = '2.7.3',
    [string]$OutJar = ''
)

$ErrorActionPreference = 'Stop'
$root = Split-Path -Parent $MyInvocation.MyCommand.Definition

function Resolve-Jdk([string]$jdkPath) {
    if (-not $jdkPath) { return $null }
    if (Test-Path (Join-Path $jdkPath 'bin\javac.exe')) { return $jdkPath }
    if (Test-Path (Join-Path $jdkPath 'javac.exe')) { return (Split-Path $jdkPath -Parent) }
    return $null
}
$JavaHome = Resolve-Jdk $JavaHome
if (-not $JavaHome) {
    foreach ($c in @('C:\Program Files\Java\jdk1.8.0_471', 'C:\Program Files\Eclipse Adoptium\jdk8u512-b09', 'C:\Program Files\Zulu\zulu-8')) {
        $JavaHome = Resolve-Jdk $c
        if ($JavaHome) { break }
    }
}
if (-not $JavaHome) { throw 'JDK 8 not found. Pass -JavaHome <path-to-jdk8>.' }
Write-Host "JDK: $JavaHome"

$javac = Join-Path $JavaHome 'bin\javac.exe'
$java  = Join-Path $JavaHome 'bin\java.exe'
$jarEx = Join-Path $JavaHome 'bin\jar.exe'

$sdk = Join-Path $root 'libs\minecraft-1.12.2-forge-mcp.jar'
if (-not (Test-Path $sdk)) { throw "compile SDK missing: $sdk" }
$libJars = @($sdk) + (Get-ChildItem (Join-Path $root 'libs') -Filter *.jar | Where-Object { $_.FullName -ne $sdk } | ForEach-Object { $_.FullName })
$cp = $libJars -join ';'

$toolCp = (Join-Path $root 'tools\deobf-tools.jar') + ';' + ((Get-ChildItem (Join-Path $root 'tools\lib') -Filter *.jar | ForEach-Object { $_.FullName }) -join ';')

$build   = Join-Path $root 'build'
$classes = Join-Path $build 'classes'
$stage   = Join-Path $build 'stage'
Remove-Item $build -Recurse -Force -ErrorAction SilentlyContinue
New-Item -ItemType Directory -Force -Path $classes, $stage | Out-Null

Write-Host '== compile'
Get-ChildItem (Join-Path $root 'src\main\java') -Recurse -Filter *.java |
    ForEach-Object { $_.FullName } | Set-Content (Join-Path $build 'sources.txt') -Encoding ASCII
& $javac -source 8 -target 8 -encoding UTF-8 -nowarn -d $classes -cp $cp "@$(Join-Path $build 'sources.txt')"
if ($LASTEXITCODE -ne 0) { throw 'compilation failed' }

Write-Host '== reobf (MCP -> SRG)'
$classesJar = Join-Path $build 'classes.jar'
$srgJar     = Join-Path $build 'classes-srg.jar'
& $jarEx cf $classesJar -C $classes .
& $java -cp $toolCp RemapTool remap $classesJar $srgJar (Join-Path $root 'tools\mcp2srg.map')
if ($LASTEXITCODE -ne 0) { throw 'reobf failed' }

Write-Host '== package'
Push-Location $stage
& $jarEx xf $srgJar
Pop-Location
Copy-Item (Join-Path $root 'src\main\resources\*') $stage -Recurse -Force

if (-not $OutJar) { $OutJar = Join-Path $build "libs\vexview-$Version-deobf.jar" }
New-Item -ItemType Directory -Force -Path (Split-Path $OutJar) | Out-Null
if (Test-Path $OutJar) { Remove-Item $OutJar -Force }
& $jarEx cf $OutJar -C $stage .
if ($LASTEXITCODE -ne 0) { throw 'jar packaging failed' }

Write-Host "OK -> $OutJar"



