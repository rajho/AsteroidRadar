package com.udacity.asteroidradar.domain

import android.os.Parcelable
import com.udacity.asteroidradar.data.local.AsteroidDb
import kotlinx.parcelize.Parcelize


@Parcelize
data class Asteroid(
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
) : Parcelable

fun List<Asteroid>.asDatabaseModel(): List<AsteroidDb> {
    return map {
        AsteroidDb(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }
}