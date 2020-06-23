package com.ajijul.livetracking.ui.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ajijul.livetracking.R
import com.ajijul.livetracking.ui.viewmodel.MainViewModel
import com.ajijul.livetracking.ui.viewmodel.StatisticsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticsFragment : Fragment(R.layout.fragment_statics) {
    private val viewModel : StatisticsViewModel by viewModels()

}