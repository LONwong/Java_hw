Java_hw — Java 21 (LTS) upgrade instructions

**Requirements:**
- JDK 21 (LTS)

**Install JDK 21 (Windows)**
- Recommended builds: Eclipse Temurin (Adoptium) or Microsoft Build of OpenJDK.
- Download: https://adoptium.net/ or https://aka.ms/download-jdk/windows
- Install using the installer and note the install path (e.g. `C:\Program Files\Eclipse Adoptium\jdk-21`).

**Set environment variables (persistent) — cmd.exe**
- Open an elevated `cmd.exe` and run (adjust path to your install):

```
setx JAVA_HOME "C:\Program Files\Eclipse Adoptium\jdk-21"
setx PATH "%JAVA_HOME%\bin;%PATH%"
```
- Close and re-open your terminal after `setx` so changes take effect.

**Verify installation**
```
java -version
javac -version
```
Both should report a 21.x version.

**Compile the project (from repository root) — cmd.exe**
This repository contains plain `.java` source files (no `pom.xml` or `build.gradle`). Use these commands to compile all sources into an `out` directory:

```
rem remove any previous sources file
if exist sources.txt del sources.txt
rem collect all .java files recursively
for /R %%f in (*.java) do @echo %%f >> sources.txt
rem create output directory
if not exist out mkdir out
rem compile
javac -d out @sources.txt
```
If running interactively in cmd (not from a batch file), replace `%%f` with `%f` in the `for` loop.

**Run a class**
- To run `Main` in `HW4` package/folder (example):
```
java -cp out HW4.Main
```
- If the class is at top-level with no package, run `java -cp out Main` instead.

**Notes & Next Steps**
- There are no build files (`pom.xml` / `build.gradle`) in this repository, so upgrading to Java 21 is done by installing JDK 21 and compiling with it.
- If you later add Maven or Gradle, update `maven.compiler.source`/`target` or `java toolchain` to `21`.

If you want, I can:
- Install JDK 21 for you (requires tool access/permission),
- Add a simple `build.bat` to automate the compile/run steps,
- Or convert the project to use Maven/Gradle and set Java 21 toolchain automatically.