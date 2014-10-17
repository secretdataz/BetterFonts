## Building BetterFonts

# Build instructions

The latest release of BetterFonts can be downloaded from: https://secretdataz.github.io/BetterFonts

This document describes how to build BetterFonts from the source files. The following software packages are required:

* The Java JDK
* Minecraft Forge Source (Provided)
* Minecraft Coder Pack (MCP or Mod Coder Pack or whatever they called)

Build steps may be complicated. Sorry for that :(

# Steps

First, install all required software.

## 1. Download and install the Java JDK

### Windows

See http://www.oracle.com/technetwork/java/javase/downloads/index.html

Be sure to remember the install location.

### Linux

    sudo apt-get install openjdk-7-jdk

### Mac OS X

See https://developer.apple.com/downloads/index.action?name=for%20Xcode%20-

Look for the Java Developer Package.

## 2. Set up development environment

Use gradle if you have Gradle installed.
Use gradlew or gradlew.bat instead if you don't have Gradle installed.

### If you have Gradle installed

    gradle setupDecompWorkspace
    
## 3. Copy FontRenderer source code from ForgeGradle temp directory

    cp build/tmp/recompSrc/net/minecraft/client/gui/FontRenderer.java src/main/java/net/minecraft/client/gui/FontRenderer.java
	
## 4. Apply patch file to FontRenderer.java

    patch src/main/java/net/minecraft/client/gui/FontRenderer.java < FontRenderer1.7.10.java.patch

## 5. Compile SRG version of BetterFonts

    gradle build
	
## 6. Get compiled FontRenderer

At this point. You will have a compiled jar inside build/libs folder.
Open it up and extract net/minecraft/client/gui/FontRenderer.class out.

## 7. Non-SRG reobfuscate the FontRenderer

Open your MCP directory. Put your FontRenderer.class in bin/minecraft/net/minecraft/client/gui
If it doesn't exist try recompiling the game in MCP once.
After that, run reobfuscate.bat or reobfuscate.sh.

## 8. Repack BetterFonts JAR

Get your obfuscated class from reobf/minecraft folder. It may have weird name like bbu.class instead of its old name.
Put it in your BetterFonts jar file from Gradle folder.