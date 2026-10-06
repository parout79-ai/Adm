# Adam Studio 0.3 — Engine Plan

## Output target
- 1280x720 (720p)
- 20 seconds per logical scene
- 16 FPS target for the mobile profile
- MP4/H.264 final delivery

## Architecture
UI -> StoryPlanner -> CharacterBible -> Scene queue -> EngineAdapter -> Mobile runtime -> Media composer

## Primary engine
Qualcomm MobileWan is the intended on-device engine adapter. The public MobileWan project is inference code, not a ready Android APK/runtime, so the Android runtime must be integrated separately.

## Important
The current APK project intentionally does not fake video generation. Until a native Android runtime is connected, the UI reports that the engine is pending.
