package com.domain.weatherapp.weather.data.repo

import com.domain.weatherapp.core.data.remote.Result
import com.domain.weatherapp.weather.data.model.WeatherList
import com.domain.weatherapp.weather.data.model.WeatherModel

interface WeatherRepository{

    suspend fun getWeatherByCity(city: String): Result<WeatherModel>
    suspend fun getWeatherByCoord(lat: String, lon: String): Result<WeatherModel>
    suspend fun getWeatherForecast(lat: String, lon: String): Result<WeatherList>
}