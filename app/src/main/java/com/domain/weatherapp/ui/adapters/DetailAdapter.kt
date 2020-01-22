package com.domain.weatherapp.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.domain.weatherapp.weather.data.model.TransformWeather
import com.domain.weatherapp.weather.data.model.WeatherModel
import com.domain.weatherapp.ui.fragments.SubDetailFragment

class DetailAdapter : FragmentStateAdapter {

    //var list: MutableList<TransformWeather>? = null
    var weather: WeatherModel? = null
    lateinit var list:MutableList<TransformWeather>

    constructor(fragmentActivity: FragmentActivity,list: MutableList<TransformWeather>) : super(fragmentActivity) {this.list = list   }

    constructor(fragment: Fragment) : super(fragment) {}
    constructor(fragmentManager: FragmentManager,lifecycle: Lifecycle,list: MutableList<TransformWeather>) : super(fragmentManager, lifecycle) { this.list = list   }

    override fun createFragment(position: Int): Fragment {
        return SubDetailFragment.newInstance(list.get(position))
    }

    override fun getItemCount(): Int {


        return list.size
    }

}