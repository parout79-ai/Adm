package com.adamstudio.app

import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.adamstudio.app.engine.MobileWanEngine
import com.adamstudio.app.model.CharacterBible
import com.adamstudio.app.model.Scene
import com.adamstudio.app.model.VideoSettings
import com.adamstudio.app.planner.StoryPlanner

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { AdamStudioApp(this) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdamStudioApp(context: Context) {
    var story by remember { mutableStateOf("") }
    var scenes by remember { mutableStateOf(emptyList<Scene>()) }
    var status by remember { mutableStateOf("جاهز") }
    val settings = remember { VideoSettings() }
    val device = remember {
        val info = ActivityManager.MemoryInfo()
        (context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager).getMemoryInfo(info)
        DeviceUi(
            manufacturer = android.os.Build.MANUFACTURER,
            model = android.os.Build.MODEL,
            soc = if (android.os.Build.VERSION.SDK_INT >= 31) "${android.os.Build.SOC_MANUFACTURER} ${android.os.Build.SOC_MODEL}" else "غير متاح",
            ramGb = info.totalMem / 1024 / 1024 / 1024,
            abi = android.os.Build.SUPPORTED_ABIS.firstOrNull() ?: "غير معروف"
        )
    }

    MaterialTheme {
        Scaffold(topBar = { TopAppBar(title = { Text("Adam Studio • 0.3") }) }) { padding ->
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item {
                    Text("استوديو فيديو محلي للأطفال", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    Text("بدون Credits أو API خارجي — الهدف تشغيل المحرك محلياً على الهاتف.")
                }
                item {
                    Card(Modifier.fillMaxWidth()) { Column(Modifier.padding(16.dp)) {
                        Text("إعدادات الإخراج", fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        Text("الدقة: ${settings.width} × ${settings.height} (720p)")
                        Text("مدة المشهد: ${settings.durationSeconds} ثانية")
                        Text("FPS: ${settings.fps}")
                        Text("الصيغة: ${settings.format}")
                    }}
                }
                item {
                    OutlinedTextField(
                        value = story,
                        onValueChange = { story = it },
                        modifier = Modifier.fillMaxWidth().height(190.dp),
                        label = { Text("اكتب قصة آدم") },
                        placeholder = { Text("مثال: آدم يلعب في الحديقة... ثم يتعلم درساً عن الصدق.") }
                    )
                }
                item {
                    Button(
                        onClick = {
                            scenes = StoryPlanner(CharacterBible()).createScenes(story)
                            status = if (scenes.isEmpty()) "اكتب القصة أولاً" else "تم إنشاء ${scenes.size} مشهد • كل مشهد 20 ثانية"
                        }, modifier = Modifier.fillMaxWidth()
                    ) { Text("تحليل القصة وإنشاء المشاهد") }
                }
                item {
                    Card(Modifier.fillMaxWidth()) { Column(Modifier.padding(16.dp)) {
                        Text("المحرك", fontWeight = FontWeight.Bold)
                        Text("الأولوية: MobileWan / Qualcomm mobile runtime")
                        Text("الحالة: ${if (MobileWanEngine().isAvailable()) "جاهز" else "بانتظار ربط Runtime الحقيقي"}")
                    }}
                }
                item {
                    Card(Modifier.fillMaxWidth()) { Column(Modifier.padding(16.dp)) {
                        Text("الجهاز", fontWeight = FontWeight.Bold)
                        Text("${device.manufacturer} ${device.model}")
                        Text("SoC: ${device.soc}")
                        Text("RAM: ${device.ramGb} GB")
                        Text("ABI: ${device.abi}")
                    }}
                }
                item { Text(status, style = MaterialTheme.typography.titleMedium) }
                items(scenes, key = { it.id }) { scene ->
                    Card(Modifier.fillMaxWidth()) { Column(Modifier.padding(14.dp)) {
                        Text(scene.title, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(5.dp))
                        Text("${scene.durationSeconds} ثانية • 1280×720")
                        Spacer(Modifier.height(5.dp))
                        Text(scene.storyText)
                        Spacer(Modifier.height(5.dp))
                        Text(scene.prompt, style = MaterialTheme.typography.bodySmall)
                    }}
                }
                item { Text("ملاحظة: هذه النسخة تجهز المشروع والمحرك؛ توليد الفيديو الفعلي يحتاج Runtime MobileWan أصلي على Android/Adreno.", style = MaterialTheme.typography.bodySmall) }
            }
        }
    }
}

data class DeviceUi(val manufacturer: String, val model: String, val soc: String, val ramGb: Long, val abi: String)
