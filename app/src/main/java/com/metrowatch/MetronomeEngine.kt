package com.metrowatch

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.PowerManager
import android.os.VibrationEffect
import android.os.VibratorManager
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
    private val vibrator: android.os.Vibrator =
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            (context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager).defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as android.os.Vibrator
        }
    private var toneGenerator: ToneGenerator? = null

    private val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
    private var wakeLock: PowerManager.WakeLock? = null

    private var job: Job? = null

    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning

    private val _bpm = MutableStateFlow(120)
    val bpm: StateFlow<Int> = _bpm

    private val _beatCount = MutableStateFlow(0)
    val beatCount: StateFlow<Int> = _beatCount

    private val _timeSignature = MutableStateFlow(TimeSignature.FOUR_FOUR)
    val timeSignature: StateFlow<TimeSignature> = _timeSignature

    private val _soundVolume = MutableStateFlow(80)
    val soundVolume: StateFlow<Int> = _soundVolume

    private val _vibrationIntensity = MutableStateFlow(80)
    val vibrationIntensity: StateFlow<Int> = _vibrationIntensity

    init {
        recreateToneGenerator()
    }

    fun setBpm(newBpm: Int) {
        _bpm.value = newBpm.coerceIn(40, 240)
    }

    fun setTimeSignature(newTimeSignature: TimeSignature) {
        _timeSignature.value = newTimeSignature
        if (_isRunning.value) {
            _beatCount.value = 0
        }
    }

    fun nextTimeSignature() {
        val currentIndex = TimeSignature.entries.indexOf(_timeSignature.value)
        val nextIndex = (currentIndex + 1) % TimeSignature.entries.size
        setTimeSignature(TimeSignature.entries[nextIndex])
    }

    fun setSoundVolume(volume: Int) {
        _soundVolume.value = volume.coerceIn(0, 100)
        recreateToneGenerator()
    }

    fun setVibrationIntensity(intensity: Int) {
        _vibrationIntensity.value = intensity.coerceIn(0, 100)
    }

    private fun recreateToneGenerator() {
        toneGenerator?.release()
        toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, _soundVolume.value)
    }

    fun start() {
        if (_isRunning.value) return

        _isRunning.value = true
        _beatCount.value = 0

        acquireWakeLock()

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
        releaseWakeLock()
    }

    private fun acquireWakeLock() {
        if (wakeLock == null) {
            wakeLock = powerManager.newWakeLock(
                PowerManager.PARTIAL_WAKE_LOCK,
                "MetroWatch::MetronomeBeat"
            )
        }
        wakeLock?.acquire()
    }

    @Suppress("SameParameterValue")
    private fun releaseWakeLock() {
        wakeLock?.let {
            if (it.isHeld) it.release()
        }
    }

    private fun beat() {
        val isAccent = (_beatCount.value % _timeSignature.value.beatsPerMeasure) == 0

        // Vibration - stronger for accent beat, scaled by user intensity
        if (vibrator.hasVibrator() && _vibrationIntensity.value > 0) {
            val duration = if (isAccent) 80L else 50L
            val baseAmplitude = if (isAccent) 255 else 180
            val scaledAmplitude = (baseAmplitude * _vibrationIntensity.value / 100).coerceIn(1, 255)
            val effect = VibrationEffect.createOneShot(duration, scaledAmplitude)
            vibrator.vibrate(effect)
        }

        // Sound - higher pitch for accent beat
        if (_soundVolume.value > 0) {
            val tone = if (isAccent) ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD else ToneGenerator.TONE_PROP_BEEP
            toneGenerator?.startTone(tone, 50)
        }
    }

    fun release() {
        stop()
        toneGenerator?.release()
        toneGenerator = null
        releaseWakeLock()
        wakeLock = null
    }
}
