package ru.shvets.myappretrofit.view.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ru.shvets.myappretrofit.R
import ru.shvets.myappretrofit.data.weather.Weather
import ru.shvets.myappretrofit.databinding.FragmentWeatherBinding
import ru.shvets.myappretrofit.util.Resource
import ru.shvets.myappretrofit.util.hide
import ru.shvets.myappretrofit.util.show

class WeatherFragment : Fragment(R.layout.fragment_weather) {
    private lateinit var _binding: FragmentWeatherBinding
    private val mBinding get() = _binding
//    private lateinit var actionBar: ActionBar

    private val weatherViewModel: WeatherViewModel by viewModels()
    lateinit var weatherAdapter: WeatherAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(layoutInflater, container, false)
//        actionBar = (activity as AppCompatActivity).supportActionBar!!
//        actionBar.setTitle(R.string.title_weather)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            val divider = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            addItemDecoration(divider)
        }

        weatherViewModel.weather.observe(viewLifecycleOwner, Observer { response ->

            when (response) {
                is Resource.Success -> {
                    mBinding.progressBar.hide()

                    response.data?.let { list ->

                        val sortedList = list.sortedWith(object : Comparator<Weather> {
                            override fun compare(p0: Weather, pi: Weather): Int {
                                if (p0.city > pi.city) {
                                    return 1
                                }
                                if (p0.city == pi.city) {
                                    return 0
                                }
                                return -1
                            }
                        })

                        weatherAdapter = WeatherAdapter(sortedList)
                        mBinding.recyclerView.adapter = weatherAdapter

//                        newsAdapter.differ.submitList(newsResponse.articles.toList())
                    }
                }
                is Resource.Error -> {
                    mBinding.progressBar.hide()
                    response.message?.let { message ->
                        Toast.makeText(activity, "An error occurred: $message", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                is Resource.Loading -> {
                    mBinding.progressBar.show()
                }
            }
        })
    }
}