package com.metrowatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.*

class MainActivity : ComponentActivity() {
    private lateinit var metronome: MetronomeEngine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        metronome = MetronomeEngine(this)

        setContent {
            MetroWatchTheme {
                MetronomeScreen(metronome)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        metronome.release()
    }
}

@Composable
fun MetroWatchTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = MaterialTheme.colors.copy(
            primary = Color(0xFF1E88E5),
            onPrimary = Color.White,
            background = Color.Black
        ),
        content = content
    )
}

@Composable
fun MetronomeScreen(metronome: MetronomeEngine) {
    val isRunning by metronome.isRunning.collectAsState()
    val bpm by metronome.bpm.collectAsState()
    val beatCount by metronome.beatCount.collectAsState()
    val timeSignature by metronome.timeSignature.collectAsState()

    Scaffold(
        timeText = { TimeText() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // BPM Display
            Text(
                text = "$bpm",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = if (isRunning) Color(0xFF4CAF50) else Color.White
            )

            Text(
                text = "BPM",
                fontSize = 16.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Time Signature Display
            Text(
                text = timeSignature.display,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E88E5)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Beat Counter
            if (isRunning) {
                val currentBeat = (beatCount % timeSignature.beatsPerMeasure) + 1
                Text(
                    text = "$currentBeat/${timeSignature.beatsPerMeasure}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (currentBeat == 1) Color(0xFF4CAF50) else Color.White
                )

                Spacer(modifier = Modifier.height(12.dp))
            }

            // BPM Control Buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CompactChip(
                    onClick = { metronome.setBpm(bpm - 5) },
                    label = {
                        Text("-5", fontSize = 14.sp)
                    },
                    colors = ChipDefaults.chipColors(
                        backgroundColor = Color.DarkGray
                    )
                )

                CompactChip(
                    onClick = { metronome.setBpm(bpm - 1) },
                    label = {
                        Text("-1", fontSize = 14.sp)
                    },
                    colors = ChipDefaults.chipColors(
                        backgroundColor = Color.DarkGray
                    )
                )

                CompactChip(
                    onClick = { metronome.setBpm(bpm + 1) },
                    label = {
                        Text("+1", fontSize = 14.sp)
                    },
                    colors = ChipDefaults.chipColors(
                        backgroundColor = Color.DarkGray
                    )
                )

                CompactChip(
                    onClick = { metronome.setBpm(bpm + 5) },
                    label = {
                        Text("+5", fontSize = 14.sp)
                    },
                    colors = ChipDefaults.chipColors(
                        backgroundColor = Color.DarkGray
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Time Signature Control Buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TimeSignature.entries.forEach { ts ->
                    CompactChip(
                        onClick = { metronome.setTimeSignature(ts) },
                        label = {
                            Text(ts.display, fontSize = 12.sp)
                        },
                        colors = ChipDefaults.chipColors(
                            backgroundColor = if (timeSignature == ts) Color(0xFF1E88E5) else Color.DarkGray
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Start/Stop Button
            Button(
                onClick = {
                    if (isRunning) {
                        metronome.stop()
                    } else {
                        metronome.start()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (isRunning) Color(0xFFE53935) else Color(0xFF4CAF50)
                ),
                modifier = Modifier.size(80.dp)
            ) {
                Text(
                    text = if (isRunning) "STOP" else "START",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
