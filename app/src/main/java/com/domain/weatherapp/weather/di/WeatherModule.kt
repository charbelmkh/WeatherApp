package com.domain.weatherapp.weather.di

import com.domain.weatherapp.core.di.BaseModule
import com.domain.weatherapp.core.di.connection.ConnectionManager
import com.domain.weatherapp.weather.data.remote.services.WeatherService
import com.domain.weatherapp.weather.data.remote.source.WeatherDataSource
import com.domain.weatherapp.weather.data.remote.source.impl.WeatherDataSourceImpl
import com.domain.weatherapp.weather.data.repo.WeatherRepository
import com.domain.weatherapp.weather.data.repo.impl.WeatherRepositoryImpl
import com.domain.weatherapp.weather.domain.WeatherUseCase
import com.domain.weatherapp.weather.domain.impl.WeatherUseCaseImpl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory

@Module(
    includes = [
        WeatherActivityBuilderModule::class, WeatherFragmentBuildersModule::class, WeatherViewModelBuilderModule::class]
)
class WeatherModule : BaseModule() {

    @Provides
    fun provideWeatherRemoteDataSource(weatherService: WeatherService): WeatherDataSource {
       return WeatherDataSourceImpl(weatherService)
    }

    @Provides
    fun provideWeatherService(
        okhttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ) = provideService(okhttpClient, converterFactory, WeatherService::class.java)



    @Provides
    fun provideWeatherRepository(dataSource:WeatherDataSource):WeatherRepository{
        return WeatherRepositoryImpl(dataSource)
    }

    @Provides
    fun provideWeatherUseCase(weatherRepository: WeatherRepository,connectionManager: ConnectionManager): WeatherUseCase =
        WeatherUseCaseImpl(
            weatherRepository,
            connectionManager
        )


}