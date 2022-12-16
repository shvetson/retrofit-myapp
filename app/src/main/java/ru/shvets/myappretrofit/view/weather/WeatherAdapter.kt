package ru.shvets.myappretrofit.view.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.shvets.myappretrofit.data.weather.Weather
import ru.shvets.myappretrofit.databinding.ItemWeatherBinding

class WeatherAdapter(
    private val items: List<Weather>
): RecyclerView.Adapter<WeatherAdapter.StartViewHolder>() {

    inner class StartViewHolder(
        private val binding: ItemWeatherBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Weather) {

            binding.apply {
                textViewCity.text = item.name
                textViewTemp.text = item.main.temp.toString()
                textViewPrecipitation.text = item.weather[0].main
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWeatherBinding.inflate(inflater, parent, false)
        return StartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StartViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}