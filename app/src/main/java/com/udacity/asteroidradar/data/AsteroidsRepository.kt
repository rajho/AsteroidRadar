package com.udacity.asteroidradar.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.data.local.AsteroidRadarDatabase
import com.udacity.asteroidradar.data.local.asDomainModel
import com.udacity.asteroidradar.data.network.AsteroidApi
import com.udacity.asteroidradar.data.network.PictureOfDay
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.asDatabaseModel
import com.udacity.asteroidradar.utils.Constants
import com.udacity.asteroidradar.utils.getCurrentDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class AsteroidsRepository(private val database: AsteroidRadarDatabase) {



    val asteroids: LiveData<List<Asteroid>> = Transformations.map(
        database.asteroidDao.getRelevantAsteroids(
            Calendar.getInstance().getCurrentDate("yyyy-MM-dd")
        )
    ) {
        it.asDomainModel()
    }

    suspend fun getPictureOfDay() = AsteroidApi.retrofitService.getApod(Constants.API_KEY)

    private val refreshAsteroidsCallback = object : Callback<String> {
        override fun onResponse(call: Call<String>, response: Response<String>) {
            val body = response.body()
            body?.let {
                val asteroids = parseAsteroidsJsonResult(JSONObject(it))

                GlobalScope.launch(Dispatchers.IO) {
                    database.asteroidDao.insertAll(asteroids.asDatabaseModel())
                }
            }

        }

        override fun onFailure(call: Call<String>, t: Throwable) {
            t.message?.let {
                Log.e(AsteroidsRepository::class.java.simpleName, it)
            }
        }

    }

    fun refreshAsteroids() {
        val startDate = Calendar.getInstance().getCurrentDate("yyyy-MM-dd")

        AsteroidApi.retrofitService.getAsteroids(startDate, null, Constants.API_KEY)
            .enqueue(refreshAsteroidsCallback)
    }

    fun refreshTodayAsteroids() {
        val startDate = Calendar.getInstance().getCurrentDate("yyyy-MM-dd")

        AsteroidApi.retrofitService.getAsteroids(startDate, startDate, Constants.API_KEY)
            .enqueue(refreshAsteroidsCallback)
    }
}