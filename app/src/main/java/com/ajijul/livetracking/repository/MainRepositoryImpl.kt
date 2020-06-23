package com.ajijul.livetracking.repository

import androidx.lifecycle.LiveData
import com.ajijul.livetracking.db.FilterType
import com.ajijul.livetracking.db.Run
import com.ajijul.livetracking.db.RunDAO
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(val dao: RunDAO) : MainRepository {
    override suspend fun insertRun(run: Run) {
        println("Insert Called")
        dao.insertRun(run)
    }

    override suspend fun deleteRun(run: Run) {
        dao.deleteRun(run)
    }

    override fun getOrderedData(filterType: FilterType): LiveData<List<Run>> {
        return dao.getOrderedData(filterType)
    }

    override fun getTotalAvg(filterType: FilterType): LiveData<Long> {
        return dao.getTotalAvg(filterType)
    }
}