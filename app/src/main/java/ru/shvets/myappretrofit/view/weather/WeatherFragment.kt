package ru.shvets.myappretrofit.view.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ru.shvets.myappretrofit.R
import ru.shvets.myappretrofit.data.weather.Weather
import ru.shvets.myappretrofit.databinding.FragmentWeatherBinding
import ru.shvets.myappretrofit.util.hide
import ru.shvets.myappretrofit.util.show

class WeatherFragment : Fragment(R.layout.fragment_weather) {
    lateinit var binding: FragmentWeatherBinding
    private val weatherViewModel: WeatherViewModel by viewModels()
    lateinit var weatherAdapter: WeatherAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherBinding.inflate(layoutInflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding = FragmentWeatherBinding.bind(view)

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