package com.sleepsounds.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val soundCategories = listOf(
    "Rain" to listOf("Light Rain", "Heavy Rain", "Thunderstorm", "Rain on Window"),
    "Nature" to listOf("Forest Birds", "Ocean Waves", "Cricket Night", "Water Stream"),
    "White Noise" to listOf("White Noise", "Pink Noise", "Brown Noise", "Fan Sound"),
    "Ambient" to listOf("Coffeeshop", "Fireplace", "Wind Chimes", "Tibetan Bowl"),
)

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MainScreen() {
    var selectedSounds by remember { mutableStateOf(setOf<String>()) }
    var volume by remember { mutableFloatStateOf(0.8f) }
    var isPlaying by remember { mutableStateOf(false) }
    var timerMin by remember { mutableIntStateOf(60) }

    Scaffold(topBar = { CenterAlignedTopAppBar(title = { Text("Sleep Sounds", fontWeight = FontWeight.Bold) }) }) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).padding(20.dp)) {
            soundCategories.forEach { (cat, sounds) ->
                Text(cat, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, modifier = Modifier.padding(top = 12.dp, bottom = 8.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(sounds) { sound ->
                        val selected = sound in selectedSounds
                        Card(
                            modifier = Modifier.width(100.dp).clickable {
                                selectedSounds = if (selected) selectedSounds - sound else selectedSounds + sound
                            },
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface)
                        ) {
                            Column(Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Default.MusicNote, null, tint = if (selected) Color.White else MaterialTheme.colorScheme.MaterialTheme.colorScheme.oncolorScheme.surfaceVariant, modifier = Modifier.size(24.dp))
                                Text(sound, fontSize = 11.sp, color = if (selected) Color.White else MaterialTheme.colorScheme.MaterialTheme.colorScheme.oncolorScheme.surfaceVariant, maxLines = 2)
                            }
                        }
                    }
                }
            }
            Spacer(Modifier.weight(1f))
            Card(shape = RoundedCornerShape(20.dp)) {
                Column(Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Volume", fontSize = 14.sp)
                        Spacer(Modifier.weight(1f))
                        Slider(value = volume, onValueChange = { volume = it }, modifier = Modifier.width(150.dp))
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Timer: {timerMin} min", fontSize = 14.sp)
                        Spacer(Modifier.weight(1f))
                        if (timerMin > 0) {
                            TextButton(onClick = { timerMin = if (timerMin >= 120) 0 else timerMin + 15 }) { Text("{if (timerMin == 0) "Off" else timerMin.toString() + "min"}") }
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                    Button(onClick = { isPlaying = !isPlaying }, modifier = Modifier.fillMaxWidth().height(48.dp),
                           shape = RoundedCornerShape(14.dp)) {
                        Icon(if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow, null)
                        Spacer(Modifier.width(8.dp))
                        Text(if (isPlaying) "Playing..." else "Start Playing", fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}
