package com.ajijul.livetracking.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ajijul.livetracking.R
import com.ajijul.livetracking.db.FilterType
import com.ajijul.livetracking.db.Run
import com.ajijul.livetracking.db.RunDAO
import com.ajijul.livetracking.helper.Constants
import com.ajijul.livetracking.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        navigateToTrackingFragmentIfNeeded(intent)
        bottomNavigationView.setupWithNavController(navHostFragment.findNavController())
        findNavController(R.id.navHostFragment)
            .addOnDestinationChangedListener { _, destination, _ ->
                when(destination.id){
                    R.id.runFragment,R.id.statisticsFragment,R.id.settingsFragment
                        -> bottomNavigationView.visibility = View.VISIBLE
                    else -> bottomNavigationView.visibility = View.GONE
                }
            }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        navigateToTrackingFragmentIfNeeded(intent)
    }

    private fun navigateToTrackingFragmentIfNeeded(intent: Intent?){
        if (intent?.action == Constants.ACTION_SHOW_TRACKING_FRAGMENT){
            navHostFragment.findNavController().navigate(R.id.action_global_trackingFragment)
        }
    }
}
