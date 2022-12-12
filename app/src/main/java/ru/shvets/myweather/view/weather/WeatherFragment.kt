package ru.shvets.myweather.view.weather

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ru.shvets.myweather.R
import ru.shvets.myweather.data.weather.Weather
import ru.shvets.myweather.databinding.FragmentStartBinding
import ru.shvets.myweather.databinding.FragmentWeatherBinding
import ru.shvets.myweather.util.hide
import ru.shvets.myweather.util.show

class WeatherFragment : Fragment(R.layout.fragment_weather) {
    lateinit var binding: FragmentWeatherBinding
    private val weatherViewModel: WeatherViewModel by viewModels()
    lateinit var weatherAdapter: WeatherAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWeatherBinding.bind(view)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        binding.progressBar.show()
        weatherViewModel.weather.observe(viewLifecycleOwner) { list ->

            val sortedList = list.sortedWith(object : Comparator<Weather> {
                override fun compare(p0: Weather, pi: Weather): Int {
                    if (p0.name > pi.name) {
                        return 1
                    }
                    if (p0.name == pi.name) {
                        return 0
                    }
                    return -1
                }
            })

            weatherAdapter = WeatherAdapter(sortedList)
            binding.recyclerView.adapter = weatherAdapter

            binding.progressBar.hide()
        }
    }
}