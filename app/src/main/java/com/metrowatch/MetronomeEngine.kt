package com.metrowatch

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.VibrationEffect
import android.os.Vibrator
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MetronomeEngine(private val context: Context) {
    private val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    private val toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 100)

    private var job: Job? = null

    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning

    private val _bpm = MutableStateFlow(120)
    val bpm: StateFlow<Int> = _bpm

    private val _beatCount = MutableStateFlow(0)
    val beatCount: StateFlow<Int> = _beatCount

    fun setBpm(newBpm: Int) {
        _bpm.value = newBpm.coerceIn(40, 240)
    }

    fun start() {
        if (_isRunning.value) return

        _isRunning.value = true
        _beatCount.value = 0

        job = CoroutineScope(Dispatchers.Default).launch {
            while (isActive && _isRunning.value) {
                beat()
                _beatCount.value++

                val intervalMs = (60000 / _bpm.value).toLong()
                delay(intervalMs)
            }
        }
    }

    fun stop() {
        _isRunning.value = false
        job?.cancel()
        job = null
        _beatCount.value = 0
    }

    private fun beat() {
        // Vibration
        if (vibrator.hasVibrator()) {
            val effect = VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(effect)
        }

        // Sound
        toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP, 50)
    }

    fun release() {
        stop()
        toneGenerator.release()
    }
}
