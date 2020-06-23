package com.ajijul.livetracking.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajijul.livetracking.db.FilterType
import com.ajijul.livetracking.db.Run
import com.ajijul.livetracking.repository.MainRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @ViewModelInject constructor(
    val mainRepository: MainRepository
): ViewModel() {


    fun insertRun(run : Run){
      viewModelScope.launch {
          mainRepository.insertRun(run)
      }
    }

    fun getTotalAvg(filterType: FilterType):LiveData<Long>{
        return mainRepository.getTotalAvg(filterType)
    }
}