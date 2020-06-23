package com.ajijul.livetracking.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RunDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRun(run: Run)

    @Delete
    suspend fun deleteRun(run: Run)

    /*@
      Query("select * from coin ORDER BY
      CASE :order
      WHEN 'percent_change_24h' THEN percent_change_24h
      WHEN 'other_column_name' THEN other_column_name
      END asc limit :numberOfCoins")
   fun getAllTop(order: String, numberOfCoins: Int): Flowable<List<CoinDB>>*/

    @Query(
        "SELECT * from running_table ORDER BY " +
                "        CASE :filterType " +
                "        WHEN 'timestamp'  THEN timestamp " +
                "        WHEN 'timeInMillis' THEN timeInMillis " +
                "        WHEN 'caloriesBurned' THEN caloriesBurned " +
                "        WHEN 'avgSpeedInKMH'  THEN avgSpeedInKMH " +
                "        WHEN 'distanceInMeter' THEN distanceInMeter " +
                "        END DESC"
    )
     fun getOrderedData(filterType: FilterType) : LiveData<List<Run>>

    @Query(
        "SELECT CASE :filterType " +
                "        WHEN 'timeInMillis' THEN SUM(timeInMillis) " +
                "        WHEN 'caloriesBurned' THEN SUM(caloriesBurned) " +
                "        WHEN 'avgSpeedInKMH'  THEN AVG(avgSpeedInKMH) " +
                "        WHEN 'distanceInMeter' THEN SUM(distanceInMeter) END" +
                "        FROM running_table"
    )
    fun getTotalAvg(filterType: FilterType) : LiveData<Long>

}

enum class FilterType(val type: String) {

    TIMESTAMP("timestamp"),
    TIMEINMILLI("timeInMillis"),
    CALLORIES("caloriesBurned"),
    SPEED("avgSpeedInKMH"),
    DISTANCE("distanceInMeter")

}