package com.domain.weatherapp.core.di

import android.app.Application
import com.domain.weatherapp.App
import com.domain.weatherapp.weather.di.WeatherActivityBuilderModule
import com.domain.weatherapp.weather.di.WeatherModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ViewModelFactoryModule::class,
        WeatherModule::class
    ]


)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(application: App)
}
