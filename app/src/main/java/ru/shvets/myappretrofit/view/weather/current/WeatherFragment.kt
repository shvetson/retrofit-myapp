package ru.shvets.myappretrofit.view.weather.current

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ru.shvets.myappretrofit.R
import ru.shvets.myappretrofit.databinding.FragmentWeatherBinding
import ru.shvets.myappretrofit.util.Extensions.hide
import ru.shvets.myappretrofit.util.Extensions.show
import ru.shvets.myappretrofit.util.ListCity
import ru.shvets.myappretrofit.util.Resource
import ru.shvets.myappretrofit.view.weather.WeatherViewModel

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
        setupRecyclerView()
        setupBundleAndClickItem()
        getData()
    }

    private fun setupRecyclerView() {
        mBinding.recyclerView.apply {
            weatherAdapter = WeatherAdapter()
            adapter = weatherAdapter
            layoutManager = LinearLayoutManager(context)
            val divider = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            addItemDecoration(divider)
        }
    }

    private fun setupBundleAndClickItem() {
        weatherAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putParcelable("city", it)
            }

            findNavController().navigate(
                R.id.action_weatherFragment_to_forecastFragment,
                bundle
            )
        }
    }

    private fun getData() {
        weatherViewModel.getCurrentWeather(ListCity.listCity)

        weatherViewModel.weather.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    mBinding.progressBar.hide()

                    response.data?.let { list ->
                        weatherAdapter.differ.submitList(list)
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