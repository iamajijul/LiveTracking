package com.ajijul.livetracking.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import com.ajijul.livetracking.R
import com.ajijul.livetracking.helper.Constants.ACTION_PAUSE_AND_RESUME_SERVICE
import com.ajijul.livetracking.helper.Constants.ACTION_SHOW_TRACKING_FRAGMENT
import com.ajijul.livetracking.helper.Constants.ACTION_START_SERVICE
import com.ajijul.livetracking.helper.Constants.ACTION_STOP_SERVICE
import com.ajijul.livetracking.helper.Constants.NOTIFICATION_CHANNEL_ID
import com.ajijul.livetracking.helper.Constants.NOTIFICATION_ID
import com.ajijul.livetracking.helper.Constants.NOTIFICATION_NAME
import com.ajijul.livetracking.ui.MainActivity
import timber.log.Timber

class TrackingService : LifecycleService() {

    var isFirstRun = true
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (it.action) {
                ACTION_START_SERVICE -> {
                    Timber.d("Action start service")
                }
                ACTION_PAUSE_AND_RESUME_SERVICE -> {
                    if (isFirstRun) {
                        startForegroundService()
                        isFirstRun = false
                    } else {
                        Timber.d("Action resume service")

                    }

                }
                ACTION_STOP_SERVICE -> {
                    Timber.d("Action stop service")

                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun startForegroundService() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }
        var notitificationBuilder = NotificationCompat.Builder(
            applicationContext,
            NOTIFICATION_CHANNEL_ID
        )
            .setAutoCancel(false)
            .setOngoing(true)
            .setSmallIcon(R.drawable.ic_directions_run_black_24dp)
            .setContentTitle(getString(R.string.running_app))
            .setContentText("00:00:00")
            .setContentIntent(getMainActitivityPendingIntent())
        startForeground(NOTIFICATION_ID, notitificationBuilder.build())
    }

    private fun getMainActitivityPendingIntent() = PendingIntent.getActivity(
        this, 0,
        Intent(this, MainActivity::class.java).apply {
            action = ACTION_SHOW_TRACKING_FRAGMENT
        }, FLAG_UPDATE_CURRENT
    )

    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel =
            NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_NAME, IMPORTANCE_LOW)
        notificationManager.createNotificationChannel(channel)
    }
}