/*
 * MetroWatch - Metronome for Wear OS
 * Copyright (C) 2026 hyangminj
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package com.metrowatch

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.flow.StateFlow

class MetronomeService : Service() {

    companion object {
        private const val CHANNEL_ID = "metronome_channel"
        private const val NOTIFICATION_ID = 1
    }

    private val binder = MetronomeBinder()
    private lateinit var engine: MetronomeEngine

    val isRunning: StateFlow<Boolean> get() = engine.isRunning
    val bpm: StateFlow<Int> get() = engine.bpm
    val beatCount: StateFlow<Int> get() = engine.beatCount
    val timeSignature: StateFlow<TimeSignature> get() = engine.timeSignature
    val soundVolume: StateFlow<Int> get() = engine.soundVolume
    val vibrationIntensity: StateFlow<Int> get() = engine.vibrationIntensity

    inner class MetronomeBinder : Binder() {
        fun getService(): MetronomeService = this@MetronomeService
    }

    override fun onCreate() {
        super.onCreate()
        engine = MetronomeEngine(this)
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (engine.isRunning.value) {
            startForegroundWithNotification()
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onDestroy() {
        engine.release()
        super.onDestroy()
    }

    fun startMetronome() {
        engine.start()
        // Promote to foreground service to keep running in background
        startForegroundService(Intent(this, MetronomeService::class.java))
    }

    fun stopMetronome() {
        engine.stop()
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    fun setBpm(newBpm: Int) = engine.setBpm(newBpm)

    fun nextTimeSignature() = engine.nextTimeSignature()

    fun setSoundVolume(volume: Int) = engine.setSoundVolume(volume)

    fun setVibrationIntensity(intensity: Int) = engine.setVibrationIntensity(intensity)

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "메트로놈",
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = "메트로놈 실행 중"
            setShowBadge(false)
        }
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    private fun startForegroundWithNotification() {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("MetroWatch")
            .setContentText("메트로놈 실행 중")
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            startForeground(
                NOTIFICATION_ID, notification,
                ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
            )
        } else {
            startForeground(NOTIFICATION_ID, notification)
        }
    }
}
