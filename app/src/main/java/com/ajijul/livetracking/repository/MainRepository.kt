package com.ajijul.livetracking.repository

import androidx.lifecycle.LiveData
import com.ajijul.livetracking.db.FilterType
import com.ajijul.livetracking.db.Run

interface MainRepository {

    suspend fun insertRun(run: Run)
    suspend fun deleteRun(run: Run)
    fun getOrderedData(filterType: FilterType): LiveData<List<Run>>
    fun getTotalAvg(filterType: FilterType): LiveData<Long>

}