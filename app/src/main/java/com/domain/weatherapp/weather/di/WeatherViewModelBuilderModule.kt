package com.domain.weatherapp.weather.di

import androidx.lifecycle.ViewModel
import com.domain.weatherapp.core.di.ViewModelKey
import com.domain.weatherapp.ui.viewmodel.WeatherViewModel


import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class WeatherViewModelBuilderModule {

    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewModel::class)
    abstract fun bindThemeViewModel(viewModel: WeatherViewModel): ViewModel


}
