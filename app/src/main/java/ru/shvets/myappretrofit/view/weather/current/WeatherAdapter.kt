package ru.shvets.myappretrofit.view.weather.current

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.shvets.myappretrofit.data.weather.Weather
import ru.shvets.myappretrofit.databinding.ItemWeatherBinding
import ru.shvets.myappretrofit.util.Constants.Companion.WEATHER_ICON_URL
import ru.shvets.myappretrofit.util.Extensions.concat
import ru.shvets.myappretrofit.util.Extensions.roundDouble

class WeatherAdapter: ListAdapter<Weather, WeatherAdapter.ViewHolder>(ItemCallback) {

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

                root.setOnClickListener {
                    onItemClickListener?.let { it(item) }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWeatherBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    object ItemCallback : DiffUtil.ItemCallback<Weather>() {
        override fun areItemsTheSame(oldItem: Weather, newItem: Weather): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Weather, newItem: Weather): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, ItemCallback)

    private var onItemClickListener: ((Weather) -> Unit)? = null

    fun setOnItemClickListener(listener: (Weather) -> Unit) {
        onItemClickListener = listener
    }
}