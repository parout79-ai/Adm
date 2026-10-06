# Adam Studio — Architecture

## Goal
Local Android-first children's video generator. The phone is the primary runtime; no paid API or credits are required by the app.

## Engine strategy
1. Primary candidate: MobileWan, Qualcomm AI Research, mobile-optimized adaptation of Wan2.2-5B.
2. Fallback: Wan2.1 T2V 1.3B at 480p.
3. Before production: benchmark both on the target Honor X9d and verify Android/Adreno execution. Do not assume desktop PyTorch repositories run directly on Android.

## Pipeline
Story -> scene planner -> character bible -> prompt builder -> local video engine -> clip validator -> FFmpeg compositor -> final MP4.

## Character consistency
A persistent Character Bible stores appearance, clothing, age, style and negative constraints. Every scene prompt is generated from this canonical record.

## First benchmark
- T2V: 480x832 target
- Short clip first; do not start with 10 seconds
- Measure RAM, peak thermal state, render time, dropped frames, output size
- Then test image-conditioned generation for Adam

## Android layers
- UI: Kotlin + Jetpack Compose
- Engine interface: VideoEngine
- Native runtime adapter: Vulkan/Qualcomm optimized backend (implementation pending benchmark)
- Media: Android MediaCodec / FFmpeg-based compositor as needed
- Storage: app-scoped project folders

## Important
MobileWan's public repository currently provides an inference sampler and Python/PyTorch dependencies; an Android APK backend is not supplied by that repository. The Android runtime must therefore be ported/implemented separately rather than simply bundling the Python project.
