package com.domain.weatherapp.weather.data.remote.source

import com.domain.weatherapp.core.data.remote.Result
import com.domain.weatherapp.weather.data.model.WeatherList
import com.domain.weatherapp.weather.data.model.WeatherModel

interface WeatherDataSource {


        suspend fun getWeatherByCity(city:String) : Result<WeatherModel>



      suspend fun  getWeatherByCoord(lat:String,lon:String) :Result<WeatherModel>


      suspend fun  getWeatherForecast(lat: String,lon: String) :Result<WeatherList>
}