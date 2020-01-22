package com.domain.weatherapp.weather.data.remote.services

import com.domain.weatherapp.weather.data.model.WeatherList
import com.domain.weatherapp.weather.data.model.WeatherModel
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {


    @GET("weather?units=metric")
    suspend fun getWeatherByCity(@Query("q") city: String): Response<WeatherModel>

    @GET("weather?units=metric")
    suspend fun getWeatherByCoord(
                                  @Query("lat") lat: String,
                                  @Query("lon") lon: String): Response<WeatherModel>

    @GET("forecast?units=metric")
    suspend fun getWeatherForecast(
        @Query("lat") lat: String,
        @Query("lon") lon: String
    ): Response<WeatherList>


}
