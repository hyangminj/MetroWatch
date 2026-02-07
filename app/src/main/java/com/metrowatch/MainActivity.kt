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
    private var metronomeService: MetronomeService? = null
    private var bound = false

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MetronomeService.MetronomeBinder
            metronomeService = binder.getService()
            bound = true
            setContent {
                MetroWatchTheme {
                    MetronomeScreen(metronomeService!!.engine)
                }
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            metronomeService = null
            bound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Start and bind to the foreground service
        val serviceIntent = Intent(this, MetronomeService::class.java)
        startForegroundService(serviceIntent)
        bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE)

        // Show loading state until service connects
        setContent {
            MetroWatchTheme {
                Box(
                    modifier = Modifier.fillMaxSize().background(Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Loading...", color = Color.Gray)
                }
            }
        }
    }

    override fun onDestroy() {
        if (bound) {
            // Stop service only if metronome is not running
            val isRunning = metronomeService?.engine?.isRunning?.value == true
            unbindService(connection)
            bound = false
            if (!isRunning) {
                stopService(Intent(this, MetronomeService::class.java))
            }
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
fun MetronomeScreen(metronome: MetronomeEngine) {
    val isRunning by metronome.isRunning.collectAsState()
    val bpm by metronome.bpm.collectAsState()
    val beatCount by metronome.beatCount.collectAsState()
    val timeSignature by metronome.timeSignature.collectAsState()
    val soundVolume by metronome.soundVolume.collectAsState()
    val vibrationIntensity by metronome.vibrationIntensity.collectAsState()

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
                        onClick = { metronome.setBpm(bpm - 5) },
                        label = { Text("-5", fontSize = 14.sp) },
                        colors = ChipDefaults.chipColors(backgroundColor = Color.DarkGray)
                    )
                    CompactChip(
                        onClick = { metronome.setBpm(bpm - 1) },
                        label = { Text("-1", fontSize = 14.sp) },
                        colors = ChipDefaults.chipColors(backgroundColor = Color.DarkGray)
                    )
                    CompactChip(
                        onClick = { metronome.setBpm(bpm + 1) },
                        label = { Text("+1", fontSize = 14.sp) },
                        colors = ChipDefaults.chipColors(backgroundColor = Color.DarkGray)
                    )
                    CompactChip(
                        onClick = { metronome.setBpm(bpm + 5) },
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
                        .clickable { metronome.nextTimeSignature() }
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
                            onClick = { metronome.setSoundVolume(soundVolume - 10) },
                            label = { Text("-", fontSize = 16.sp) },
                            colors = ChipDefaults.chipColors(backgroundColor = Color.DarkGray)
                        )
                        CompactChip(
                            onClick = { metronome.setSoundVolume(soundVolume + 10) },
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
                            onClick = { metronome.setVibrationIntensity(vibrationIntensity - 10) },
                            label = { Text("-", fontSize = 16.sp) },
                            colors = ChipDefaults.chipColors(backgroundColor = Color.DarkGray)
                        )
                        CompactChip(
                            onClick = { metronome.setVibrationIntensity(vibrationIntensity + 10) },
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
                            metronome.stop()
                        } else {
                            metronome.start()
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
