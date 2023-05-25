package com.example.weatherappandroid.adapters

import android.text.TextUtils.concat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherappandroid.databinding.WeatherItemBinding
import com.example.weatherappandroid.models.list
import com.example.weatherappandroid.utils.Utils
import com.squareup.picasso.Picasso

class WeatherAdapter(var weatherList: MutableList<list>, val listener: IWeatherListener) : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {
    interface IWeatherListener {
        fun onItemTap(weather: list)
    }

    class WeatherViewHolder(val binding: WeatherItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
       val inflater = LayoutInflater.from(parent.context)
        val binding = WeatherItemBinding.inflate(inflater, parent, false)
        return  WeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val weather = weatherList[position]
        holder.binding.tvCity.text = concat(weather.name, " , ", weather.sys.country)
        holder.binding.tvTemp.text = Utils.convertToCelsius(weather.main.temp)
        Picasso.with(holder.binding.root.context).load(Utils.getWeatherImage(weather.weather.first().icon)).resize(50, 50).centerCrop()
            .into(holder.binding.ivWeatherIcon)
        holder.binding.container.setOnClickListener {
            listener.onItemTap(weather)
        }
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }

    fun updateList(weatherList: MutableList<list>) {
        val oldSize = this.weatherList.size
        this.weatherList = weatherList
        if (weatherList.size <= oldSize) {
            notifyItemRangeRemoved(0, oldSize)
            notifyItemRangeInserted(0, weatherList.size)
        } else {
            notifyItemRangeChanged(0, this.weatherList.size)
        }
    }
}


