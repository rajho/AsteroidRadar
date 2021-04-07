package com.udacity.asteroidradar.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.data.AsteroidsRepository
import com.udacity.asteroidradar.data.local.AsteroidRadarDatabase
import com.udacity.asteroidradar.data.network.PictureOfDay
import com.udacity.asteroidradar.domain.Asteroid
import kotlinx.coroutines.launch
import java.io.IOException
import java.lang.Exception

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val asteroidsRepository =
        AsteroidsRepository(AsteroidRadarDatabase.getInstance(application))

    private val _asteroids = asteroidsRepository.asteroids
    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    private val _navigateToAsteroidDetail = MutableLiveData<Asteroid?>()
    val navigateToAsteroidDetail: LiveData<Asteroid?>
        get() = _navigateToAsteroidDetail


    init {
        refreshDataFromRepository()
        getPictureOfDay()
    }

    private fun getPictureOfDay() {
        viewModelScope.launch {
            try {
                _pictureOfDay.value = asteroidsRepository.getPictureOfDay()
            } catch (e: Exception) {
                e.message?.let {
                    Log.e(MainViewModel::class.java.simpleName, it)
                }
            }
        }
    }

    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                asteroidsRepository.refreshAsteroids()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun onAsteroidItemClicked(asteroid: Asteroid) {
        _navigateToAsteroidDetail.value = asteroid
    }

    fun onAsteroidItemCompleted() {
        _navigateToAsteroidDetail.value = null
    }
}