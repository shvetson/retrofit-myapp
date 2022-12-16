package ru.shvets.myappretrofit.model.repository

import retrofit2.Call
import ru.shvets.myappretrofit.data.weather.Weather

/** @author Oleg Shvets shvetson@gmail.com on 2022-09-25 */

interface WeatherRepository {

    fun getCurrentWeather(
        q: String,
        appid: String,
        units: String,
        lang: String
    ): Call<Weather>
}