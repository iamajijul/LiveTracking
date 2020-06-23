package com.ajijul.livetracking.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ajijul.livetracking.R
import com.ajijul.livetracking.db.FilterType
import com.ajijul.livetracking.db.Run
import com.ajijul.livetracking.db.RunDAO
import com.ajijul.livetracking.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel : MainViewModel by viewModels()
    @Inject
    lateinit var dao: RunDAO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.e("RunDao","RUN DAO"+dao.hashCode().toString())
        viewModel.insertRun(Run(null,3214,322.0f,124313,214343,123))
        viewModel.getTotalAvg(FilterType.TIMESTAMP).observe(this, Observer {
            Log.e("RunDao","Data saved"+it)

        })
    }
}
