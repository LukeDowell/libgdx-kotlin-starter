# Kotlin LibGDX Quickstart Project

This project serves as a launching point for LibGDX projects written in Kotlin. 

## KTX Libraries

This project comes packaged with KTX extension libraries. These serve to smooth some of the rougher interactions between
LibGDX and Kotlin, as well as providing some quality-of-life utilities such as lightweight dependency injection. 

If you decide not to use any of these libraries, feel free to remove them from the root build.gradle file.

[Ktx's Github Page](https://github.com/libktx/ktx)

## FAQ
> badlogic.jpg can't be found!

In IntelliJ, go to Edit Run Configurations and set the Working Directory to /android/assets

> No HTML5 project?

From the LibGDX wiki page on Kotlin:

"Due to how GWT works, you will not be able to use the HTML5 target with Kotlin. This could be fixed in the future by 
using Kotlin’s JavaScript back-end. It might be possible to utilize [TeaVM](https://github.com/konsoletyper/teavm) as a 
replacement for GWT, though."

> Android-related Gradle sync errors?!

Open Project Settings (Command + ;), go to Platform Settings -> Sdk and add your Android SDK
