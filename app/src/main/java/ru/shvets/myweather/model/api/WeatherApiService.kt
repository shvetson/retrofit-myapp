package ru.shvets.myweather.model.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.shvets.myweather.data.weather.Weather

interface WeatherApiService {

    @GET("data/2.5/weather")
    fun getCurrentWeather(
        @Query("q") q: String,
        @Query("appid") appid: String,
        @Query("units") units: String,
        @Query("lang") lang: String
    ): Call<Weather>
}