package com.domain.weatherapp.weather.di


import com.domain.weatherapp.ui.fragments.WeatherForecastFragment
import com.domain.weatherapp.ui.fragments.SubDetailFragment
import com.domain.weatherapp.ui.fragments.WeatherListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class WeatherFragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeDetailFragment(): WeatherForecastFragment

    @ContributesAndroidInjector
    abstract fun contributeWeatherFragment(): WeatherListFragment

    @ContributesAndroidInjector
    abstract fun contributeSubFragment(): SubDetailFragment

}
