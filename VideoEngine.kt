package com.adamstudio.app.engine

import com.adamstudio.app.model.Scene
import com.adamstudio.app.model.VideoSettings

interface VideoEngine {
    val name: String
    fun isAvailable(): Boolean
    fun render(scene: Scene, settings: VideoSettings): EngineResult
}

data class EngineResult(val success: Boolean, val message: String, val outputPath: String? = null)

/** Adapter for the eventual on-device MobileWan/Qualcomm runtime. */
class MobileWanEngine : VideoEngine {
    override val name = "MobileWan (On-device)"
    override fun isAvailable(): Boolean = false
    override fun render(scene: Scene, settings: VideoSettings): EngineResult =
        EngineResult(false, "Runtime MobileWan لم يُربط بعد بواجهة Android/Adreno")
}

class WanFallbackEngine : VideoEngine {
    override val name = "Wan 2.1 1.3B (Fallback)"
    override fun isAvailable(): Boolean = false
    override fun render(scene: Scene, settings: VideoSettings): EngineResult =
        EngineResult(false, "Runtime الاحتياطي غير مثبت بعد")
}
