package ru.shvets.myappretrofit.view.weather.forecast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.shvets.myappretrofit.data.weather.ForecastResponse
import ru.shvets.myappretrofit.databinding.ItemForecastBinding
import ru.shvets.myappretrofit.util.Constants.Companion.WEATHER_ICON_URL
import ru.shvets.myappretrofit.util.Extensions.concat
import ru.shvets.myappretrofit.util.Extensions.formatDateShort
import ru.shvets.myappretrofit.util.Extensions.roundDouble
import ru.shvets.myappretrofit.util.Extensions.toDate

class ForecastAdapter : RecyclerView.Adapter<ForecastAdapter.StartViewHolder>() {

    var items: List<ForecastResponse.HourlyWeather> = listOf()

    inner class StartViewHolder(
        private val binding: ItemForecastBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ForecastResponse.HourlyWeather) {
            binding.apply {
                textViewTime.text = formatDateShort(toDate(item.dt))
                textViewTemp.text = roundDouble(item.main.temp, 10.0).toString()
                    .concat("\u00B0")
                    .concat("C")
                textViewPrecipitation.text = item.weather[0].description

                val url = WEATHER_ICON_URL + item.weather[0].icon + ".png"

                Picasso.get()
                    .load(url)
                    .resize(50, 50)
                    .centerCrop()
                    .into(imageViewIcon)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemForecastBinding.inflate(inflater, parent, false)
        return StartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StartViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}