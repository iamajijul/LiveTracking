package com.ajijul.livetracking.helper

import android.graphics.Color

object Constants {

    val RUNNING_DATABASE_NAME = "running_database.db"

    val REQUEST_LOCATION_PERMISSION_CODE = 0

    val ACTION_START_SERVICE = "action_start_service"
    val ACTION_START_AND_RESUME_SERVICE = "action_start_and_resume_service"
    val ACTION_STOP_SERVICE = "action_stop_service"
    val ACTION_PAUSE_SERVICE = "action_pause_service"
    val ACTION_SHOW_TRACKING_FRAGMENT = "action_show_tracking_fragment"


    val LOCATION_UPDATE_INTERVAL = 5000L
    val FASTEST_LOCATION_INTERVAL = 2000L


    val NOTIFICATION_CHANNEL_ID = "tracking_channel"
    val NOTIFICATION_NAME = "live_tracking"
    val NOTIFICATION_ID = 1

    var POLYLINE_COLOR = Color.RED
    var POLYLINE_WIDTH = 8f
    var MAP_ZOOM = 15f

}