package com.domain.weatherapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.domain.weatherapp.R
import com.domain.weatherapp.weather.data.model.TransformWeather
import com.domain.weatherapp.weather.data.model.WeatherModel
import com.domain.weatherapp.ui.adapters.SubDetailAdapter
import kotlinx.android.synthetic.main.recyler_view.*

private const val FORE_CAST_KEY = "FORE_CAST_KEY"
class SubDetailFragment : Fragment() {
    lateinit var data: TransformWeather
    lateinit var weather: WeatherModel
    var adapter: SubDetailAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getParcelable<TransformWeather>(FORE_CAST_KEY) as TransformWeather
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.recyler_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = SubDetailAdapter(
            data.WList

        )
        list_weather.adapter = adapter



    }
    companion object {

        @JvmStatic
        fun newInstance(param1: TransformWeather)
                         =
            SubDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(FORE_CAST_KEY, param1)

                }
            }
    }
}
