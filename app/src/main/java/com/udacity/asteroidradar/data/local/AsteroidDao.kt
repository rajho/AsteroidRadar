package com.udacity.asteroidradar.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AsteroidDao {

    @Query("select * from asteroid where close_approach_date >= :currentDate order by close_approach_date")
    fun getRelevantAsteroids(currentDate : String): LiveData<List<AsteroidDb>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(asteroids: List<AsteroidDb>)
}