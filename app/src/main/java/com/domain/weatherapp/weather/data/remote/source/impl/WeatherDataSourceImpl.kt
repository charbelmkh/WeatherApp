package com.domain.weatherapp.weather.data.remote.source.impl

import com.domain.weatherapp.core.data.remote.BaseDataSource
import com.domain.weatherapp.core.data.remote.Result
import com.domain.weatherapp.weather.data.remote.services.WeatherService
import com.domain.weatherapp.weather.data.remote.source.WeatherDataSource
import retrofit2.Response
import javax.inject.Inject

class WeatherDataSourceImpl @Inject constructor(private val weatherService: WeatherService):BaseDataSource(),WeatherDataSource {


    override  suspend fun getWeatherByCity(city:String) = getResult { weatherService.getWeatherByCity(city) }



    override suspend fun getWeatherByCoord(lat:String,lon:String) = getResult { weatherService.getWeatherByCoord(lat,lon) }


    override suspend fun getWeatherForecast(lat: String,
                                   lon: String) = getResult { weatherService.getWeatherForecast(lat,lon) }




}