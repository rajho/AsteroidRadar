package com.udacity.asteroidradar.data.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.utils.Constants.BASE_URL
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface AsteroidsService {
    @GET("neo/rest/v1/feed")
    fun getAsteroids(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String?,
        @Query("api_key") apiKey: String
    ): Call<String>

    /** Astronomic picture of the day */
    @GET("planetary/apod")
    suspend fun getApod(@Query("api_key") apiKey: String): PictureOfDay
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

object AsteroidApi {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build();

    val retrofitService: AsteroidsService by lazy {
        retrofit.create(AsteroidsService::class.java)
    }
}

