package com.domain.weatherapp.core.di

import android.app.Application
import com.domain.weatherapp.core.di.connection.ConnectionManager
import com.domain.weatherapp.core.di.connection.ConnectionManagerImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [CoreDataModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideConnectionManager(context: Application) : ConnectionManager
    {
        return ConnectionManagerImpl(context)
    }




}
