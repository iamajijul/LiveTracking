package com.ajijul.livetracking.services

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.Looper
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ajijul.livetracking.R
import com.ajijul.livetracking.helper.Constants.ACTION_PAUSE_SERVICE
import com.ajijul.livetracking.helper.Constants.ACTION_SHOW_TRACKING_FRAGMENT
import com.ajijul.livetracking.helper.Constants.ACTION_START_AND_RESUME_SERVICE
import com.ajijul.livetracking.helper.Constants.ACTION_START_SERVICE
import com.ajijul.livetracking.helper.Constants.ACTION_STOP_SERVICE
import com.ajijul.livetracking.helper.Constants.FASTEST_LOCATION_INTERVAL
import com.ajijul.livetracking.helper.Constants.LOCATION_UPDATE_INTERVAL
import com.ajijul.livetracking.helper.Constants.NOTIFICATION_CHANNEL_ID
import com.ajijul.livetracking.helper.Constants.NOTIFICATION_ID
import com.ajijul.livetracking.helper.Constants.NOTIFICATION_NAME
import com.ajijul.livetracking.helper.TrackingUtility
import com.ajijul.livetracking.ui.MainActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import timber.log.Timber

typealias Polyline = MutableList<LatLng>
typealias Polylines = MutableList<Polyline>

class TrackingService : LifecycleService() {

    var isFirstRun = true
    lateinit var fusedLocationProviderClient : FusedLocationProviderClient

    companion object {
        val isTracking = MutableLiveData<Boolean>(false)
        val pathPoints = MutableLiveData<Polylines>(mutableListOf())
    }

    override fun onCreate() {
        super.onCreate()
        fusedLocationProviderClient = FusedLocationProviderClient(this)
        isTracking.observe(this, Observer {
            updateLocationTracking(it)
        })
    }


    fun pauseService(){
        isTracking.postValue(false)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (it.action) {

                ACTION_START_AND_RESUME_SERVICE -> {
                    if (isFirstRun) {
                        startForegroundService()
                        isFirstRun = false
                    } else {
                        Timber.d("Action resume service")
                        startForegroundService()
                    }

                }
                ACTION_STOP_SERVICE -> {
                    Timber.d("Action stop service")

                }
                ACTION_PAUSE_SERVICE->{
                    pauseService()
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun addEmptyPolyline() = pathPoints.value?.apply {
        add(mutableListOf())
        pathPoints.postValue(this)
    } ?: pathPoints.postValue(mutableListOf(mutableListOf()))

    private fun startForegroundService() {
        addEmptyPolyline()
        isTracking.postValue(true)
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

    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult?) {
            super.onLocationResult(p0)
            if (isTracking.value!!) {
                p0?.locations?.let {
                    for (location in it) {
                        addPathPoint(location)
                        Timber.d("New Location lat : ${location.latitude}, lng : ${location.longitude}")
                    }
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun updateLocationTracking(isTracking: Boolean) {
        if (isTracking) {
            if (TrackingUtility.hasLocationPermission(this)) {
                val request = LocationRequest().apply {
                    interval = LOCATION_UPDATE_INTERVAL
                    fastestInterval = FASTEST_LOCATION_INTERVAL
                    priority = PRIORITY_HIGH_ACCURACY
                }
                fusedLocationProviderClient.requestLocationUpdates(request,locationCallback, Looper.getMainLooper())

            }
        }else{
            fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        }
    }

    private fun addPathPoint(location: Location?) {
        location?.let {
            val pos = LatLng(location.latitude, location.longitude)
            pathPoints.value?.apply {
                last().add(pos)
                pathPoints.postValue(this)
            }
        }
    }

    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel =
            NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_NAME, IMPORTANCE_LOW)
        notificationManager.createNotificationChannel(channel)
    }
}