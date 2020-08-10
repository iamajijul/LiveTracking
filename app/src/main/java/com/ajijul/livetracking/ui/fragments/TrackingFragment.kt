package com.ajijul.livetracking.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ajijul.livetracking.R
import com.ajijul.livetracking.helper.Constants
import com.ajijul.livetracking.helper.Constants.ACTION_PAUSE_SERVICE
import com.ajijul.livetracking.helper.Constants.ACTION_START_AND_RESUME_SERVICE
import com.ajijul.livetracking.helper.Constants.ACTION_STOP_SERVICE
import com.ajijul.livetracking.helper.Constants.MAP_ZOOM
import com.ajijul.livetracking.services.Polyline
import com.ajijul.livetracking.services.TrackingService
import com.ajijul.livetracking.ui.viewmodel.MainViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.PolylineOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_tracking.*
import timber.log.Timber

@AndroidEntryPoint
class TrackingFragment : Fragment(R.layout.fragment_tracking) {
    private var isTracking: Boolean = false
    private val viewModel: MainViewModel by viewModels()
    private var map: GoogleMap? = null

    private var pathPoints = mutableListOf<Polyline>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {
            map = it
            addAllPolyline()
        }

        btnToggleRun.setOnClickListener {

            toggleRun()
        }
        subscribeToObserver()
    }

    fun addAllPolyline() {
        for (polyline in pathPoints) {
            var polylineOption = PolylineOptions()
                .color(Constants.POLYLINE_COLOR)
                .width(Constants.POLYLINE_WIDTH)
                .addAll(polyline)
            map?.addPolyline(polylineOption)
        }
    }

    private fun moveCameraToPosition() {
        if (pathPoints.isNotEmpty() && pathPoints.last().isNotEmpty()) {
            map?.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    pathPoints.last().last(),
                    MAP_ZOOM
                )
            )
        }
    }

    private fun updateTracking(isTracking: Boolean) {
        this.isTracking = isTracking
        if (!isTracking) {
            btnToggleRun.text = "Start"
            btnFinishRun.visibility = View.VISIBLE
        } else {
            btnToggleRun.text = "Stop"
            btnFinishRun.visibility = View.GONE
        }
    }

    private fun toggleRun() {
        if (isTracking) {
            sendCommandToService(ACTION_PAUSE_SERVICE)
        } else {
            sendCommandToService(ACTION_START_AND_RESUME_SERVICE)
        }
    }

    fun addLatestPolylines() {
        if (pathPoints.isNotEmpty() && pathPoints.last().size > 1) {
            var preLastLatLng = pathPoints.last()[pathPoints.last().size - 2]
            var lastLatLng = pathPoints.last().last()
            var polylineOption = PolylineOptions()
                .color(Constants.POLYLINE_COLOR)
                .width(Constants.POLYLINE_WIDTH)
                .add(preLastLatLng)
                .add(lastLatLng)
            map?.addPolyline(polylineOption)
        }
    }

    private fun subscribeToObserver() {
        TrackingService.isTracking.observe(viewLifecycleOwner, Observer {
            updateTracking(it)
        })

        TrackingService.pathPoints.observe(viewLifecycleOwner, Observer {
            Timber.d("New Point fetch")
            pathPoints = it
            addLatestPolylines()
            moveCameraToPosition()
        })
    }

    private fun sendCommandToService(action: String) {
        Intent(requireContext(), TrackingService::class.java).let {
            it.action = action
            requireContext().startService(it)
        }
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }


    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }


}







