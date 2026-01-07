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

enum class TimeSignature(val beatsPerMeasure: Int, val display: String) {
    TWO_FOUR(2, "2/4"),
    THREE_FOUR(3, "3/4"),
    FOUR_FOUR(4, "4/4"),
    SIX_EIGHT(6, "6/8")
}

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

    private val _timeSignature = MutableStateFlow(TimeSignature.FOUR_FOUR)
    val timeSignature: StateFlow<TimeSignature> = _timeSignature

    fun setBpm(newBpm: Int) {
        _bpm.value = newBpm.coerceIn(40, 240)
    }

    fun setTimeSignature(newTimeSignature: TimeSignature) {
        _timeSignature.value = newTimeSignature
        if (_isRunning.value) {
            _beatCount.value = 0
        }
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
        val isAccent = (_beatCount.value % _timeSignature.value.beatsPerMeasure) == 0

        // Vibration - stronger for accent beat
        if (vibrator.hasVibrator()) {
            val duration = if (isAccent) 80L else 50L
            val amplitude = if (isAccent) VibrationEffect.DEFAULT_AMPLITUDE else 128
            val effect = VibrationEffect.createOneShot(duration, amplitude)
            vibrator.vibrate(effect)
        }

        // Sound - higher pitch for accent beat
        val tone = if (isAccent) ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD else ToneGenerator.TONE_PROP_BEEP
        toneGenerator.startTone(tone, 50)
    }

    fun release() {
        stop()
        toneGenerator.release()
    }
}
