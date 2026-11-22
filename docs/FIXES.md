# 修复记录：反混淆遗留的“调用点密钥”字符串解密

本仓库的源码来自对 VexView 2.7.3 官方 jar 的反混淆。官方 jar 里有一类字符串加密：
**解密密钥由“调用方的类名 + 方法名”拼成**（`new RuntimeException().getStackTrace()[1]`），
所以同一段密文在不同的调用点会解出不同结果。

反混淆把类名/方法名恢复成了可读名字（`VexGameMenu`、`m4` …），但当初的密文是按
**混淆后的类名/方法名**加密的，于是这些调用点在运行期解出来的不再是原文，而是乱码。

## 影响与修复

共 4 处调用点，现已全部替换成解密表（`strings3.json`）里的原文常量：

| 文件 | 位置 | 原调用 | 修复后 | 影响 |
| --- | --- | --- | --- | --- |
| `lk/vexview/ui/menu/VexGameMenu.java` | `m4(String)` | `SBPacketBuffer.m1("\"^2L")` | `"<##>"` | **游戏菜单右侧“服务器更新日志”面板全空**：`split(乱码)[3]` 抛 `PatternSyntaxException`，公告行列表始终为空 |
| `lk/vexview/gui/core/VexGui.java` | 构造器 | `VerNetManager.m2("kn_aQnm")` | `"[local]"` | GUI 背景是否本地文件的判定失效 |
| `lk/vexview/gui/core/VexImage.java` | 2 处 | `VerNetManager.m2("KN\u007fAqnm")` | `"[local]"` | 图片组件本地文件判定失效 |
| `lk/vexview/gui/core/VexMcImage.java` | `m1()` | `ChatString.m1("eKqd_kc")` | `"[local]"` | MC 图片组件本地文件判定失效 |

`[local]` 判定失效的后果：`[local]xxx.png` 这类**本地贴图会被当成 URL 去下载**，
服务器用本地图做 GUI 背景/图片/按钮时会加载失败。

> 这 4 处之外的字符串都已被反混淆阶段替换成字面量，无需处理；
> `SBPacketBuffer.m1` / `VerNetManager.m2` / `ChatString.m1` 等解密方法本身保留（保持与原 jar 的 API 一致），
> 只是不再有调用点依赖它们。

## 复核方式

`tools/verify/GameMenuProbe.java` 直接调用 `VexGameMenu.m4` 并检查解析结果：

```powershell
# 构建（会生成 build/classes）
pwsh -File .\build-local.ps1

# 编译并运行探针（需要 libs/ 里的 1.12.2 SDK）
$jdk  = '<path-to-jdk8>'
$sdk  = '.\libs\minecraft-1.12.2-forge-mcp.jar'
$libs = (Get-ChildItem .\libs -Filter *.jar | Where-Object { $_.FullName -ne $sdk } | ForEach-Object FullName) -join ';'
& "$jdk\bin\javac.exe" -d .\tools\verify\out -cp ".\build\classes;$sdk;$libs" .\tools\verify\GameMenuProbe.java
& "$jdk\bin\java.exe"  -cp ".\build\classes;$sdk;$libs;.\tools\verify\out" GameMenuProbe
```

修复前：

```
RESULT m4 threw: java.util.regex.PatternSyntaxException: Dangling meta character '?' near index 0
```

修复后：

```
RESULT url   = https://www.vexview.top
RESULT title = UpdateTitle
RESULT lines = [Update log line one, Update log line two, third line]
RESULT lineCount = 3
RESULT PASS
```

## 附：MC 1.8.9 移植版的对应情况

同一份代码移植到 1.8.9 时在本仓库之外完成，那里除了上述 4 处需要同样的修复，还有一处
**只影响 1.8.9** 的问题：语言文件名必须是 `assets/vexview/lang/en_US.lang` / `zh_CN.lang`
（地区码大写，pack_format 1）；1.11+ 才改成小写 `en_us.lang`（pack_format 3）。
本仓库面向 1.12.2，`en_us.lang` + `pack_format 3` 是正确配置，无需改动。
