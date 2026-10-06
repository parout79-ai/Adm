package com.adamstudio.app.model

data class VideoSettings(
    val width: Int = 1280,
    val height: Int = 720,
    val durationSeconds: Int = 20,
    val fps: Int = 16,
    val format: String = "MP4 (H.264)"
)

data class CharacterBible(
    val name: String = "آدم",
    val age: Int = 7,
    val description: String = "طفل عربي لطيف، ملامح طفولية، ملابس محتشمة، تصميم ثلاثي الأبعاد سينمائي مناسب للأطفال"
)

data class Scene(
    val id: Int,
    val title: String,
    val storyText: String,
    val prompt: String,
    val durationSeconds: Int = 20,
    val status: String = "جاهز"
)

data class DeviceReport(
    val manufacturer: String,
    val model: String,
    val soc: String,
    val ramGb: Long,
    val abi: String,
    val android: String
)
