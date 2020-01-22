package com.domain.weatherapp.core.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.domain.weatherapp.core.di.ViewModelFactory
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

open class BaseActivity : AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var viewModelProvider: ViewModelFactory


    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector;


    inline fun <reified T : ViewModel> injectViewModel(): T {
        return ViewModelProviders.of(this, viewModelProvider)[T::class.java]
    }

}