package ru.shvets.myappretrofit.view.weather.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import ru.shvets.myappretrofit.R
import ru.shvets.myappretrofit.databinding.FragmentForecastBinding
import ru.shvets.myappretrofit.util.Constants.Companion.WEATHER_ICON_URL
import ru.shvets.myappretrofit.util.concat
import ru.shvets.myappretrofit.util.roundDouble
import ru.shvets.myappretrofit.util.show
import ru.shvets.myappretrofit.util.upper
import ru.shvets.myappretrofit.view.weather.WeatherViewModel

class ForecastFragment : Fragment(R.layout.fragment_forecast) {
    private lateinit var _binding: FragmentForecastBinding
    private val mBinding get() = _binding
//    private lateinit var actionBar: ActionBar
    private lateinit var forecastAdapter: ForecastAdapter

    private val weatherViewModel: WeatherViewModel by viewModels()
    private val args: ForecastFragmentArgs by navArgs()

    private val city
        get() = args.city

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForecastBinding.inflate(inflater, container, false)
//        actionBar = (activity as AppCompatActivity).supportActionBar!!
//        actionBar.setTitle(R.string.title_weather_forecast)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTitle()
        setupRecyclerView()
    }

    private fun initTitle() {
        mBinding.progressBar.show()
        mBinding.apply {

                textViewCity.text = city.city
                textViewTemp.text = roundDouble(city.temp, 10.0).toString()
                    .concat("\u00B0")
                    .concat("C")

                textViewDescription.text = getString(R.string.msg_weather_feels_like)
                    .concat(" ")
                    .concat(roundDouble(city.feelsLike, 10.0).toString())
                    .concat("\u00B0")
                    .concat("C")
                    .concat("\n")
                    .concat(city.desc.upper())

                val url = WEATHER_ICON_URL + city.icon + ".png"
                Picasso.get()
                    .load(url)
                    .resize(50, 50)
                    .centerCrop()
                    .into(imageViewIcon)
        }
    }

    private fun setupRecyclerView() {
        with(mBinding.recyclerView) {
            forecastAdapter = ForecastAdapter()
            layoutManager = LinearLayoutManager(context)
            adapter = forecastAdapter

            val divider = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            addItemDecoration(divider)
        }
    }
}