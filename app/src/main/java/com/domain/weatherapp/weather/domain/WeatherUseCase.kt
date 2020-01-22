package com.domain.weatherapp.weather.domain

import androidx.lifecycle.MutableLiveData
import com.domain.weatherapp.core.data.remote.Result
import com.domain.weatherapp.weather.data.model.WeatherList
import com.domain.weatherapp.weather.data.model.WeatherModel
import kotlinx.coroutines.CoroutineScope

interface WeatherUseCase{

    fun subscribeToWeather(
        lat: String,
        lon: String,
        viewModelScope: CoroutineScope
    ): MutableLiveData<Result<WeatherModel>>

    fun subscribeToWeather(
        csvCountries: String,
        viewModelScope: CoroutineScope
    ): MutableLiveData<Result<List<WeatherModel>>>

    fun getWeatherByCoordinates(
        lat: String,
        lon: String,
        viewModelScope: CoroutineScope
    ): MutableLiveData<Result<WeatherList>>
}