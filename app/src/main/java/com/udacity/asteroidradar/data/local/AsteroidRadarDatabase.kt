package com.udacity.asteroidradar.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AsteroidDb::class], version = 1, exportSchema = false)
abstract class AsteroidRadarDatabase : RoomDatabase() {

    abstract val asteroidDao: AsteroidDao

    companion object {
        @Volatile
        private lateinit var INSTANCE: AsteroidRadarDatabase

        fun getInstance(context: Context): AsteroidRadarDatabase {
            synchronized(this) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidRadarDatabase::class.java,
                        "asteroid_radar"
                    ).build()
                }
                return INSTANCE
            }
        }
    }

}