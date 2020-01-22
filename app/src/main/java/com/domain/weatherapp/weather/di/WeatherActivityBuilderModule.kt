package com.domain.weatherapp.weather.di

import com.domain.weatherapp.ui.activities.WeatherActivity

import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module(includes = [WeatherViewModelBuilderModule::class])
abstract class WeatherActivityBuilderModule {
    @ContributesAndroidInjector(modules = [WeatherFragmentBuildersModule::class])
    abstract fun contributeMainActivity(): WeatherActivity


}
