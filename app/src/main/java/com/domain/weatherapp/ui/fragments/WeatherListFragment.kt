package com.domain.weatherapp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.domain.weatherapp.BuildConfig
import com.domain.weatherapp.R
import com.domain.weatherapp.core.data.remote.Result
import com.domain.weatherapp.core.ui.BaseFragment
import com.domain.weatherapp.core.ui.hide
import com.domain.weatherapp.core.ui.show
import com.domain.weatherapp.weather.data.model.WeatherModel
import com.domain.weatherapp.ui.viewmodel.WeatherViewModel
import com.domain.weatherapp.ui.adapters.WeatherByAreaAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_base.*
import kotlinx.android.synthetic.main.progress_bar.*
import kotlinx.android.synthetic.main.recyler_view.*
import javax.inject.Inject


class WeatherListFragment : BaseFragment(), SearchView.OnQueryTextListener {



    @Inject
    lateinit var viewModel: WeatherViewModel


    lateinit var adapter: WeatherByAreaAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.recyler_view, container, false) as ViewGroup
        return super.onCreateView(inflater, view, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list_weather.layoutManager=LinearLayoutManager(context)
        adapter = WeatherByAreaAdapter(
            mutableListOf()

        )
        list_weather.adapter = adapter
        viewModel = injectViewModel();
        Log.d("WeatherListFragment", viewModel.weatherByCity.hasObservers().toString())
        viewModel.weatherByCity.observe(viewLifecycleOwner, Observer {
            onResult(it, it.data)
        })
        if (BuildConfig.DEBUG){
            subscribedViewToViewMode("london,Beirut,Paris")
        }
    }

    private fun subscribedViewToViewMode(string: String) {
        text_message.hide()
        adapter.mValues.clear();
        viewModel.subscribeToWeather(string)


    }

    private fun onResult(
        it: Result<List<WeatherModel>>,
        data: List<WeatherModel>?
    ) {
        Log.d("WeatherListFragment", "WeatherListFragment")

        when (it.status) {
            Result.Status.LOADING -> {
                progress_bar.show()
            }
            Result.Status.SUCCESS -> {
                progress_bar.hide()

                it.data?.let {
                    adapter.mValues.addAll(it.filterNotNull())
                    adapter.notifyDataSetChanged()
                }
                if (it.data == null || data?.size==0) {
                    text_message.text = getString(R.string.no_result_found)
                    text_message.show()
                }


            }
            Result.Status.ERROR -> {
                progress_bar.hide()
                Snackbar.make(container_relative, it.message + "", Snackbar.LENGTH_LONG).show()


            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onDetach() {
        super.onDetach()

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.actionSearch)
        searchItem?.let {
            it.actionView?.let{
                val searchView=it as SearchView
                searchView.setOnQueryTextListener(this)
                searchView.queryHint = "Search"
            }
        }

        super.onCreateOptionsMenu(menu, inflater)
    }



    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            subscribedViewToViewMode(query)
        }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
    }

    override fun onPause() {
        super.onPause()
    }
}
