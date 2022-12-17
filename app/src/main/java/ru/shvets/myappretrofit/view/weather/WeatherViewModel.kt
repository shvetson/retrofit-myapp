package ru.shvets.myappretrofit.view.weather

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.shvets.myappretrofit.App
import ru.shvets.myappretrofit.data.weather.Weather
import ru.shvets.myappretrofit.util.Constants.Companion.WEATHER_API_KEY
import ru.shvets.myappretrofit.util.Constants.Companion.WEATHER_API_LANG
import ru.shvets.myappretrofit.util.Constants.Companion.WEATHER_API_METRIC
import ru.shvets.myappretrofit.util.ListCity
import ru.shvets.myappretrofit.util.Resource

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

    init {
        getCurrentWeather(ListCity.listCity)
    }

    private fun getCurrentWeather(listCity: List<String>) = viewModelScope.launch {
        _weather.postValue(Resource.Loading())

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
                    val temp = it.main.temp
                    val desc = it.weather[0].description
                    val icon = it.weather[0].icon

                    list.add(
                        Weather(
                            city = city,
                            temp = temp,
                            desc = desc,
                            icon = icon
                        )
                    )
                }
            }
        }

        if (list.isNotEmpty()) {
            _weather.postValue(Resource.Success(list))
        } else {
            _weather.postValue(Resource.Error("Error loading data!"))
        }
    }


//        private fun hasInternetConnections(): Boolean {
//            val connectivityManager = getApplication<App>().getSystemService(
//                Context.CONNECTIVITY_SERVICE
//            ) as ConnectivityManager
//
//            val activeNetwork = connectivityManager.activeNetwork ?: return false
//            val capabilities =
//                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
//
//            return when {
//                capabilities.hasTransport(TRANSPORT_WIFI) -> true
//                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
//                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
//                else -> false
//            }
//        }
//
//        private fun hasAccessURL(): Boolean {
//            return try {
//                val command = "ping -c 1 $WEATHER_BASE_URL"
//                Runtime.getRuntime().exec(command).waitFor() == 0
//            } catch (e: Exception) {
//                false
//            }
//        }
}