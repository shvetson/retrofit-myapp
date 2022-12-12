package ru.shvets.myweather.view.weather

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.*
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.shvets.myweather.App
import ru.shvets.myweather.data.weather.Weather
import ru.shvets.myweather.util.Constants.Companion.TAG
import ru.shvets.myweather.util.Constants.Companion.WEATHER_API_KEY
import ru.shvets.myweather.util.Constants.Companion.WEATHER_API_LANG
import ru.shvets.myweather.util.Constants.Companion.WEATHER_API_METRIC
import ru.shvets.myweather.util.Constants.Companion.WEATHER_BASE_URL


class WeatherViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repo = (application.applicationContext as App).weatherRepository

    private val _data: MutableLiveData<Weather> = MutableLiveData()
    val data: LiveData<Weather> = _data

    fun getWeather(city: String): LiveData<Weather> {
        val call = repo.getCurrentWeather(
            city,
            WEATHER_API_KEY,
            WEATHER_API_METRIC,
            WEATHER_API_LANG
        )

        call.enqueue(object : Callback<Weather> {
            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                _data.postValue(response.body())
                Log.d(TAG, "OnResponse Success: ${response.message()}")
            }

            override fun onFailure(call: Call<Weather>, t: Throwable) {
                Log.d(TAG, "OnFailure: ${t.message}")
            }
        })
        return _data
    }

    private val listCity = listOf(
        "Moscow",
        "Krasnogorsk",
        "Vladimir",
        "Surgut",
        "Krasnodar",
        "Rivne",
        "Saratov",
        "Kiev",
        "Minsk",
        "Vyazniki"
    )

    init {
        getWeathers(listCity)
    }

    private val _weather: MutableLiveData<List<Weather>> = MutableLiveData()
    val weather: LiveData<List<Weather>> = _weather

    private fun getWeathers(listCity: List<String>): LiveData<List<Weather>> {
        val listWeather = ArrayList<Weather>()

//        if (!hasInternetConnections()) {
//            return _weather
//        }
//
//        if (!hasAccessURL()) {
//            Log.d(Constants.TAG, "There is not access to site with data")
//            _weather.postValue(listWeather)
//            return _weather
//        }


        for (item in listCity) {

            val call = repo.getCurrentWeather(
                item,
                WEATHER_API_KEY,
                WEATHER_API_METRIC,
                WEATHER_API_LANG
            )

            call.enqueue(object : Callback<Weather> {

                override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                    response.body()?.let {
                        listWeather.add(it)
                        Log.d(TAG, "OnResponse Success: ${response.message()}")
                    }

                    if (listWeather.size == listCity.size) {
                        _weather.postValue(listWeather)
                    }
                }

                override fun onFailure(call: Call<Weather>, t: Throwable) {
                    Log.d(TAG, "OnFailure: ${t.message}")
                }
            })
        }
        return _weather
    }

    private fun hasInternetConnections(): Boolean {
        val connectivityManager = getApplication<App>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            capabilities.hasTransport(TRANSPORT_WIFI) -> true
            capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
            capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

    private fun hasAccessURL(): Boolean {
        return try {
            val command = "ping -c 1 $WEATHER_BASE_URL"
            Runtime.getRuntime().exec(command).waitFor() == 0
        } catch (e: Exception) {
            false
        }
    }
}