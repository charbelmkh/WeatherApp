package com.domain.weatherapp.core.di

import com.domain.weatherapp.weather.data.remote.services.WeatherService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class BaseModule {

    companion object {
        const val ENDPOINT = "https://api.openweathermap.org/data/2.5/"
        const val URL_END_PINT= "http://openweathermap.org/img/w/%s.png"
    }

      fun <T> provideService(okhttpClient: OkHttpClient, converterFactory: GsonConverterFactory, clazz: Class<T>): T {
        return createRetrofit(okhttpClient, converterFactory).create(clazz)
    }

      fun createRetrofit(okhttpClient: OkHttpClient, converterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ENDPOINT)
            .client(okhttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }
}