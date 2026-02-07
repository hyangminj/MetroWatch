package com.metrowatch

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState

class MainActivity : ComponentActivity() {
    private var metronomeService: MetronomeService? by mutableStateOf(null)
    private var bound = false

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MetronomeService.MetronomeBinder
            metronomeService = binder.getService()
            bound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            metronomeService = null
            bound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Only bind to the service (no startForegroundService here)
        val serviceIntent = Intent(this, MetronomeService::class.java)
        bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE)

        setContent {
            MetroWatchTheme {
                val service = metronomeService
                if (service != null) {
                    MetronomeScreen(service)
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize().background(Color.Black),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Loading...", color = Color.Gray)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        if (bound) {
            unbindService(connection)
            bound = false
        }
        super.onDestroy()
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
fun MetronomeScreen(service: MetronomeService) {
    val isRunning by service.isRunning.collectAsState()
    val bpm by service.bpm.collectAsState()
    val beatCount by service.beatCount.collectAsState()
    val timeSignature by service.timeSignature.collectAsState()
    val soundVolume by service.soundVolume.collectAsState()
    val vibrationIntensity by service.vibrationIntensity.collectAsState()

    val listState = rememberScalingLazyListState()

    Scaffold(
        timeText = { TimeText() }
    ) {
        ScalingLazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            state = listState,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(40.dp))
            }

            // BPM Display
            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
                }
            }

            // BPM Control Buttons
            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    CompactChip(
                        onClick = { service.setBpm(bpm - 5) },
                        label = { Text("-5", fontSize = 14.sp) },
                        colors = ChipDefaults.chipColors(backgroundColor = Color.DarkGray)
                    )
                    CompactChip(
                        onClick = { service.setBpm(bpm - 1) },
                        label = { Text("-1", fontSize = 14.sp) },
                        colors = ChipDefaults.chipColors(backgroundColor = Color.DarkGray)
                    )
                    CompactChip(
                        onClick = { service.setBpm(bpm + 1) },
                        label = { Text("+1", fontSize = 14.sp) },
                        colors = ChipDefaults.chipColors(backgroundColor = Color.DarkGray)
                    )
                    CompactChip(
                        onClick = { service.setBpm(bpm + 5) },
                        label = { Text("+5", fontSize = 14.sp) },
                        colors = ChipDefaults.chipColors(backgroundColor = Color.DarkGray)
                    )
                }
            }

            // Time Signature Display (tap to change)
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable { service.nextTimeSignature() }
                        .padding(12.dp)
                ) {
                    Text(
                        text = "박자 (탭해서 변경)",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = timeSignature.display,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E88E5)
                    )
                }
            }

            // Beat Counter
            if (isRunning) {
                item {
                    val currentBeat = (beatCount % timeSignature.beatsPerMeasure) + 1
                    Text(
                        text = "$currentBeat/${timeSignature.beatsPerMeasure}",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (currentBeat == 1) Color(0xFF4CAF50) else Color.White,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }

            // Sound Volume Control
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "소리: $soundVolume%",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        CompactChip(
                            onClick = { service.setSoundVolume(soundVolume - 10) },
                            label = { Text("-", fontSize = 16.sp) },
                            colors = ChipDefaults.chipColors(backgroundColor = Color.DarkGray)
                        )
                        CompactChip(
                            onClick = { service.setSoundVolume(soundVolume + 10) },
                            label = { Text("+", fontSize = 16.sp) },
                            colors = ChipDefaults.chipColors(backgroundColor = Color.DarkGray)
                        )
                    }
                }
            }

            // Vibration Intensity Control
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "진동: $vibrationIntensity%",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        CompactChip(
                            onClick = { service.setVibrationIntensity(vibrationIntensity - 10) },
                            label = { Text("-", fontSize = 16.sp) },
                            colors = ChipDefaults.chipColors(backgroundColor = Color.DarkGray)
                        )
                        CompactChip(
                            onClick = { service.setVibrationIntensity(vibrationIntensity + 10) },
                            label = { Text("+", fontSize = 16.sp) },
                            colors = ChipDefaults.chipColors(backgroundColor = Color.DarkGray)
                        )
                    }
                }
            }

            // Start/Stop Button
            item {
                Button(
                    onClick = {
                        if (isRunning) {
                            service.stopMetronome()
                        } else {
                            service.startMetronome()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (isRunning) Color(0xFFE53935) else Color(0xFF4CAF50)
                    ),
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .size(80.dp)
                ) {
                    Text(
                        text = if (isRunning) "STOP" else "START",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}
