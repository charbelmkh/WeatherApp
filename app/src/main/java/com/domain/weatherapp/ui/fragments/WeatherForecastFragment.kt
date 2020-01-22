package com.domain.weatherapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

import com.domain.weatherapp.R
import com.domain.weatherapp.core.data.remote.Result
import com.domain.weatherapp.core.di.BaseModule
import com.domain.weatherapp.core.ui.BaseFragment
import com.domain.weatherapp.core.ui.hide
import com.domain.weatherapp.core.ui.show
import com.domain.weatherapp.weather.data.model.WeatherModel
import com.domain.weatherapp.ui.viewmodel.WeatherViewModel
import com.domain.weatherapp.ui.adapters.DetailAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_base.*
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.progress_bar.*
import javax.inject.Inject



class WeatherForecastFragment : BaseFragment() {

    lateinit var weather: WeatherModel
    lateinit var adapter: DetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        arguments?.let {

            weather = it.getParcelable<WeatherModel>("weather")as WeatherModel
        }
    }

    @Inject
    lateinit var viewModel: WeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false) as ViewGroup
        return super.onCreateView(inflater, view, savedInstanceState)

    }


    @SuppressLint("DefaultLocale")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            adapter = DetailAdapter(
                childFragmentManager,
                lifecycle,
                mutableListOf()
            )
            view_pager.adapter = adapter
            TabLayoutMediator(tabs, view_pager) { tab, position ->
                tab.text = adapter.list.get(position).dt_txt
                view_pager.setCurrentItem(tab.position, true)
            }.attach()
            viewModel = injectViewModel();
            text_message.hide()
            viewModel.subscribeToForeCast(
                weather.coord.lat.toString(),
                weather.coord.lon.toString()
            ).observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    Result.Status.LOADING -> {
                        progress_bar.show()
                    }
                    Result.Status.SUCCESS -> {
                        progress_bar.hide()

                        if (it.data == null || it.data.isEmpty()) {
                            text_message.text = getString(R.string.no_result_found)
                            text_message.show()
                        }else {
                            adapter.list.addAll(it.data)
                            adapter.notifyDataSetChanged()
                        }
                    }

                    Result.Status.ERROR -> {
                        progress_bar.hide()
                        Snackbar.make(container_relative, it.message + "", Snackbar.LENGTH_LONG).show()


                    }
                }

            })

            Glide.with(img_main.context)
                .load(BaseModule.URL_END_PINT.format(weather.weather[0].icon))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(img_main)

            txt_main_desc.text = weather.weather[0].description.toUpperCase()
            txt_main_temp.text = weather.main.temp.toString()
            txt_feels_like.text = it.getString(R.string.feels_like,weather.main.feels_like.toString())
            txt_main_city.text = weather.name

        }

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        super.onCreateOptionsMenu(menu, inflater)
    }



}
