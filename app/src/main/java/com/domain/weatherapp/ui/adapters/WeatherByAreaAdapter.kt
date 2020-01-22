package com.domain.weatherapp.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions


import com.domain.weatherapp.R
import com.domain.weatherapp.core.di.BaseModule.Companion.URL_END_PINT
import com.domain.weatherapp.ui.fragments.WeatherListFragmentDirections
import com.domain.weatherapp.weather.data.model.WeatherModel

import kotlinx.android.synthetic.main.fragment_weather_list_item.view.*

/**
 * [RecyclerView.Adapter] that can display a [WeatherModel] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class WeatherByAreaAdapter(
    val mValues: MutableList<WeatherModel>

) : RecyclerView.Adapter<WeatherByAreaAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as WeatherModel
           val direction= WeatherListFragmentDirections.actionListToForecast(item)
            v.findNavController().navigate(direction)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_weather_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        try {
            holder.desc.text = item.weather[0].description
            holder.country.text = item.name
            holder.temp_min.text = item.main.temp_min.toString()+"°C"
            holder.temp_max.text = item.main.temp_max.toString()+"°C"
            Glide.with(holder.img.context)
                .load(URL_END_PINT.format(item.weather[0].icon))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.img)
        } catch (e: Exception) {
        }

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val desc: TextView = mView.txt_description
        val country: TextView = mView.txt_city
        val temp_min: TextView = mView.txt_min_temp
        val temp_max: TextView = mView.txt_max_temp
        val img: ImageView = mView.image

        override fun toString(): String {
            return super.toString() + " '" + country.text + "'"
        }
    }
}
