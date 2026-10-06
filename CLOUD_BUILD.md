# Adam Studio — Cloud APK Build

This project is prepared for a cloud Android build from a phone.

## GitHub Actions
1. Create a GitHub repository.
2. Upload the contents of this folder (the `AdamStudio` directory itself).
3. Open **Actions** → **Build Adam Studio APK**.
4. Choose **Run workflow** if needed.
5. After the job completes, open the workflow run and download the `AdamStudio-debug-apk` artifact.
6. Extract it on the phone and install `app-debug.apk`.

The debug APK is signed by Android's debug signing configuration and is suitable for direct testing. A production release should later use a private release keystore.
