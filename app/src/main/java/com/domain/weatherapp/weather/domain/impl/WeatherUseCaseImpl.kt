package com.domain.weatherapp.weather.domain.impl

import androidx.lifecycle.MutableLiveData
import com.domain.weatherapp.core.data.remote.Result
import com.domain.weatherapp.core.di.connection.ConnectionManager
import com.domain.weatherapp.weather.data.model.WeatherList
import com.domain.weatherapp.weather.data.model.WeatherModel
import com.domain.weatherapp.weather.data.repo.WeatherRepository
import com.domain.weatherapp.weather.domain.WeatherUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject


open class WeatherUseCaseImpl @Inject constructor(private val repository: WeatherRepository,private val connectionManager: ConnectionManager


): WeatherUseCase {

    companion object {
        const val MINIMUM_COUNTRIES_ALLOWED = 3
        const val MAXIMUM_COUNTRIES_ALLOWED = 7
    }


  override  fun subscribeToWeather(
        lat: String,
        lon: String,
        viewModelScope: CoroutineScope
    ): MutableLiveData<Result<WeatherModel>> {
        val liveData = MutableLiveData<Result<WeatherModel>>()
        val offline = !connectionManager.isNetworkAvailable()
        if (offline) {
            liveData.value = Result.error("Please make sure you are connected to the internet")
        } else {
            liveData.value = Result.loading()
            viewModelScope.launch {
                val result = repository.getWeatherByCoord(lat, lon)
                liveData.postValue(result)
            }

        }


        return liveData;
    }

    override fun subscribeToWeather(
        csvCountries: String, viewModelScope: CoroutineScope
    ): MutableLiveData<Result<List<WeatherModel>>> {

        val liveData = MutableLiveData<Result<List<WeatherModel>>>()
        if (connectionManager.isNetworkAvailable()) {
            val list = csvCountries.trim().split(",")
            if (list.size < MINIMUM_COUNTRIES_ALLOWED) {
                liveData.value =
                    Result.error("Please Insert Minimum $MINIMUM_COUNTRIES_ALLOWED Cities")

            } else if (list.size > MAXIMUM_COUNTRIES_ALLOWED) {
                liveData.value =
                    Result.error("Please Insert Maximum $MAXIMUM_COUNTRIES_ALLOWED Cities")
            } else if (isValidInput(list)) {
                liveData.value = Result.error("Invalid Input")
            } else {
                liveData.value = Result.loading()
                viewModelScope.launch {

                    val weatherList =
                        list.map { area -> async { area to repository.getWeatherByCity(area) } }
                            .map { it.await() }
                            .map { it.second }
                            .map { it.data }
                            .filterNotNull()

                    liveData.postValue((Result.success(weatherList)))


                }

            }
        } else {
            liveData.value = Result.error("Please make sure you are connected to the internet")
        }

        return liveData;
    }

    override fun getWeatherByCoordinates(
        lat: String,
        lon: String,
        viewModelScope: CoroutineScope
    ): MutableLiveData<Result<WeatherList>> {
        val liveData = MutableLiveData<Result<WeatherList>>()

        val offline = !connectionManager.isNetworkAvailable()

        if (offline) {
            liveData.value = Result.error("Please make sure you are connected to the internet")
        } else {
            liveData.value = Result.loading()
            viewModelScope.launch {
                val result = repository.getWeatherForecast(
                    lat,
                    lon
                )
                liveData.postValue(result)
            }
        }



        return liveData;
    }

    private fun isValidInput(list: List<String>): Boolean {
        for (s in list) {
            if (s.trim().isEmpty()) {
                return true
            }
        }
        return false;
    }


}