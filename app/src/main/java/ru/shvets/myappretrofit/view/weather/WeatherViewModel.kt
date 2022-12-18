package ru.shvets.myappretrofit.view.weather

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.shvets.myappretrofit.App
import ru.shvets.myappretrofit.data.weather.Forecast
import ru.shvets.myappretrofit.data.weather.Weather
import ru.shvets.myappretrofit.util.Constants.Companion.WEATHER_API_KEY
import ru.shvets.myappretrofit.util.Constants.Companion.WEATHER_API_LANG
import ru.shvets.myappretrofit.util.Constants.Companion.WEATHER_API_METRIC
import ru.shvets.myappretrofit.util.ListCity
import ru.shvets.myappretrofit.util.Resource
import java.io.IOException

class WeatherViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repo = (application.applicationContext as App).weatherRepository

//    private val _data: MutableLiveData<WeatherResponse> = MutableLiveData()
//    val weatherCurrent: LiveData<WeatherResponse>
//        get() = _data
//
//    fun getWeather(city: String): LiveData<WeatherResponse> {
//        val call = repo.getCurrentWeather(
//            city,
//            WEATHER_API_KEY,
//            WEATHER_API_METRIC,
//            WEATHER_API_LANG
//        )
//
//        call.enqueue(object : Callback<WeatherResponse> {
//            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
//                _data.postValue(response.body())
//                Log.d(TAG, "OnResponse Success: ${response.message()}")
//            }
//
//            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
//                Log.d(TAG, "OnFailure: ${t.message}")
//            }
//        })
//        return _data
//    }

    private val _weather: MutableLiveData<Resource<List<Weather>>> = MutableLiveData()
    val weather: LiveData<Resource<List<Weather>>>
        get() = _weather

    private val _forecast: MutableLiveData<Forecast> = MutableLiveData()
    val forecast: LiveData<Forecast>
        get() = _forecast

    init {
        getCurrentWeather(ListCity.listCity)
    }

    private fun getCurrentWeather(listCity: List<String>) = viewModelScope.launch {
        _weather.postValue(Resource.Loading())

        try {
            if (hasInternetConnection()) {
                val list = mutableListOf<Weather>()

                for (item in listCity) {
                    val response = repo.getCurrentWeather(
                        item,
                        WEATHER_API_KEY,
                        WEATHER_API_METRIC,
                        WEATHER_API_LANG
                    )

                    if (response.isSuccessful) {
                        response.body()?.let {
                            val city = it.name
                            val lon = it.coord.lon
                            val lat = it.coord.lat
                            val temp = it.main.temp
                            val feelsLike = it.main.feelsLike
                            val desc = it.weather[0].description
                            val icon = it.weather[0].icon

                            list.add(
                                Weather(
                                    city = city,
                                    lon = lon,
                                    lat = lat,
                                    temp = temp,
                                    feelsLike = feelsLike,
                                    desc = desc,
                                    icon = icon
                                )
                            )
                        }
                    }
                }

                if (list.isNotEmpty()) {
                    _weather.postValue(Resource.Success(sortListByName(list)))
                } else {
                    _weather.postValue(Resource.Error("Error loading data!"))
                }

            } else {
                _weather.postValue(Resource.Error("No internet connection!"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> _weather.postValue(Resource.Error("Network Failure"))
                else -> _weather.postValue(Resource.Error("Conversion error"))
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<App>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities =
            connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            capabilities.hasTransport(TRANSPORT_WIFI) -> true
            capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
            capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

    private fun sortListByName(list: List<Weather>): List<Weather> {
        return list.sortedWith(object : Comparator<Weather> {
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
    }
}
