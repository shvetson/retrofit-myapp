package ru.shvets.myappretrofit.model.api.weather

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.shvets.myappretrofit.data.weather.Weather
import ru.shvets.myappretrofit.util.Constants.Companion.WEATHER_API_KEY
import ru.shvets.myappretrofit.util.Constants.Companion.WEATHER_API_LANG
import ru.shvets.myappretrofit.util.Constants.Companion.WEATHER_API_METRIC

interface WeatherAPI {

    @GET("data/2.5/weather")
    fun getCurrentWeather(
        @Query("q") q: String,
        @Query("appid") appid: String = WEATHER_API_KEY,
        @Query("units") units: String = WEATHER_API_METRIC,
        @Query("lang") lang: String = WEATHER_API_LANG
    ): Call<Weather>

    @GET("data/2.5/forecast")
    fun getForecast(
        @Query("q") q: String,
        @Query("appid") appid: String = WEATHER_API_KEY,
        @Query("units") units: String = WEATHER_API_METRIC,
        @Query("lang") lang: String = WEATHER_API_LANG
    ): Call<Weather>
}