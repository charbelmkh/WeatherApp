package com.domain.weatherapp.ui.viewmodel

import androidx.lifecycle.*
import com.domain.weatherapp.OpenForTesting
import com.domain.weatherapp.core.data.remote.Result
import com.domain.weatherapp.core.utils.DateUtils
import com.domain.weatherapp.weather.data.model.TransformWeather
import com.domain.weatherapp.weather.data.model.WeatherList
import com.domain.weatherapp.weather.data.model.WeatherModel
import com.domain.weatherapp.weather.domain.WeatherUseCase
import javax.inject.Inject


@OpenForTesting
open class WeatherViewModel @Inject constructor(private val useCase: WeatherUseCase) : ViewModel() {

    val weatherByCity: MediatorLiveData<Result<List<WeatherModel>>> = MediatorLiveData()

    val weatherByCoord: MediatorLiveData<Result<WeatherModel>> = MediatorLiveData()


    fun subscribeToWeather(lat: String, lon: String) {
        weatherByCoord.addSource(useCase.subscribeToWeather(lat, lon, viewModelScope)) {
            weatherByCoord.value = it
        }

    }


    fun subscribeToWeather(cities: String) {
        weatherByCity.addSource(useCase.subscribeToWeather(cities, viewModelScope)) {
            weatherByCity.value = it
        }

    }


    private val weatherByDay: MediatorLiveData<Result<MutableList<TransformWeather>>> =
        MediatorLiveData()

    fun subscribeToForeCast(
        lat: String,
        lon: String
    ): MediatorLiveData<Result<MutableList<TransformWeather>>> {
        val liveDataSource = useCase.getWeatherByCoordinates(lat, lon, viewModelScope);
        weatherByDay.addSource(liveDataSource, Observer {
            if (it.status == Result.Status.SUCCESS) {
                weatherByDay.value = transform(it)
            } else if (it.status == Result.Status.ERROR) {
                weatherByDay.value = Result.error(it.message)
            } else {
                weatherByDay.value = Result.loading()
            }

        })
        return weatherByDay
    }

    fun transform(it: Result<WeatherList>?): Result<MutableList<TransformWeather>> {
        if (it != null) {
            val result = it.data?.WList?.groupBy { DateUtils.dateToWeekDay(it.dt_txt) }
            var list = mutableListOf<TransformWeather>()
            result?.let {
                for (keyValue in result) {
                    val key = keyValue.key;
                    val value = keyValue.value
                    value.forEach {
                        it.dt_txt = DateUtils.dateToTime(it.dt_txt).toUpperCase()

                    }
                    list.add(TransformWeather(key, value))
                }
            }
            //?.map { it.value.map { it.copy(dt_txt = DateUtils.dateToTime(it.dt_txt)) } }
            return Result.success(list)
        }
        return Result.error("failed parsing data");
    }

}