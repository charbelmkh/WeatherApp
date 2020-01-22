package com.domain.weatherapp.weather.data.repo.impl

import com.domain.weatherapp.weather.data.remote.source.WeatherDataSource
import com.domain.weatherapp.weather.data.remote.source.impl.WeatherDataSourceImpl
import com.domain.weatherapp.weather.data.repo.WeatherRepository
import javax.inject.Inject

open class WeatherRepositoryImpl @Inject constructor(private val dataSource: WeatherDataSource):WeatherRepository {


    override  suspend fun getWeatherByCity(city:String) =  dataSource.getWeatherByCity(city)

    override suspend fun getWeatherByCoord(lat:String,lon:String)  =dataSource.getWeatherByCoord(lat,lon)

    override suspend fun getWeatherForecast(lat: String, lon: String) =dataSource.getWeatherForecast(lat, lon)
}