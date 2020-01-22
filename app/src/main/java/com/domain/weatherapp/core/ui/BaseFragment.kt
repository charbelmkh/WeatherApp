package com.domain.weatherapp.core.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.domain.weatherapp.R
import com.domain.weatherapp.core.di.ViewModelFactory
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.*
import javax.inject.Inject

open class BaseFragment : Fragment(), HasAndroidInjector {


    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var viewModelProvider: ViewModelProvider.Factory


    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector;

    inline fun <reified T : ViewModel> injectViewModel(): T {
        return ViewModelProviders.of(this, viewModelProvider)[T::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_base, container, false)
        view.clearFindViewByIdCache()
        val relative=view.findViewById(R.id.parent) as ViewGroup
        relative.addView(container)
        return view;
    }
}
