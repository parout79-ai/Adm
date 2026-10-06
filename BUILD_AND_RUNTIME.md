# Build / Runtime Notes

This project is an Android Studio project, not an APK artifact.

The 0.3 UI is configured for 1280x720 and 20-second logical scenes. The actual MobileWan generation backend is kept behind `VideoEngine` so a native Qualcomm/Android runtime can be plugged in without redesigning the UI.

Do not claim the app generates video until the runtime passes a real-device render test.
