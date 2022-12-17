package ru.shvets.myappretrofit.view.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.shvets.myappretrofit.data.weather.Weather
import ru.shvets.myappretrofit.databinding.ItemWeatherBinding
import ru.shvets.myappretrofit.util.Constants.Companion.WEATHER_ICON_URL
import ru.shvets.myappretrofit.util.concat
import ru.shvets.myappretrofit.util.roundDouble

class WeatherAdapter(
    private val items: List<Weather>
) : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    inner class ViewHolder(
        private val binding: ItemWeatherBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Weather) {

            binding.apply {
                textViewCity.text = item.city
                textViewTemp.text = roundDouble(item.temp, 10.0).toString()
                    .concat("\u00B0")
                    .concat("C")
                textViewPrecipitation.text = item.desc

                val url = WEATHER_ICON_URL + item.icon + ".png"

                Picasso.get()
                    .load(url)
                    .resize(50, 50)
                    .centerCrop()
                    .into(imageViewIcon)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWeatherBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}