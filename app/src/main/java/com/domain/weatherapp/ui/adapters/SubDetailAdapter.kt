package com.domain.weatherapp.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions


import com.domain.weatherapp.R
import com.domain.weatherapp.core.di.BaseModule.Companion.URL_END_PINT
import com.domain.weatherapp.weather.data.model.WList
import kotlinx.android.synthetic.main.item_sub_detail.view.*

import kotlinx.android.synthetic.main.fragment_weather_list_item.view.image


class SubDetailAdapter(
    val mValues: List<WList>

) : RecyclerView.Adapter<SubDetailAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sub_detail, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        try {
            holder.time.text = item.dt_txt
            holder.temperature.text = item.main.temp.toString() + "°C"
            holder.img
            Glide.with(holder.img.context)
                .load(URL_END_PINT.format(item.weather[0].icon))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.img)
        } catch (e: Exception) {
        }

        with(holder.mView) {
            tag = item

        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val time: TextView = mView.time
        val temperature: TextView = mView.tmp
        val img: ImageView = mView.image

        override fun toString(): String {
            return super.toString() + " '" + temperature.text + "'"
        }
    }
}
