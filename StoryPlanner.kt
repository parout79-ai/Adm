package com.adamstudio.app.planner

import com.adamstudio.app.model.CharacterBible
import com.adamstudio.app.model.Scene

class StoryPlanner(private val character: CharacterBible = CharacterBible()) {
    fun createScenes(story: String): List<Scene> {
        val parts = story.trim()
            .split(Regex("(?<=[.!؟])\\s+|\\n+"))
            .map { it.trim() }
            .filter { it.isNotBlank() }
            .take(12)
        if (parts.isEmpty()) return emptyList()
        return parts.mapIndexed { index, text ->
            val title = when (index) {
                0 -> "المشهد ${index + 1} • البداية"
                parts.lastIndex -> "المشهد ${index + 1} • النهاية"
                else -> "المشهد ${index + 1}"
            }
            Scene(
                id = index + 1,
                title = title,
                storyText = text,
                prompt = buildPrompt(text)
            )
        }
    }

    private fun buildPrompt(text: String): String =
        "شخصية آدم ثابتة في كل اللقطات، ${character.age} سنوات، $text. " +
        "أسلوب رسوم ثلاثية الأبعاد سينمائي لطيف للأطفال، إضاءة ناعمة، تعابير وجه واضحة، " +
        "حركة طبيعية، كاميرا سينمائية مستقرة، تفاصيل نظيفة، بدون نصوص أو شعارات أو تشوهات، " +
        "استمرارية بصرية مع المشاهد السابقة، لقطة واحدة مدتها 20 ثانية، 1280x720."
}
