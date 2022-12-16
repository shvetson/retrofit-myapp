package ru.shvets.myappretrofit.model.repository

import retrofit2.Call
import ru.shvets.myappretrofit.data.weather.Weather
import ru.shvets.myappretrofit.model.api.weather.Common

/** @author Oleg Shvets shvetson@gmail.com on 2022-09-25 */

class WeatherRepositoryImpl : WeatherRepository {
    private val weatherApi = Common.weatherAPI

    override fun getCurrentWeather(
        q: String,
        appid: String,
        units: String,
        lang: String
    ): Call<Weather> {
        return weatherApi.getCurrentWeather(q, appid, units, lang)
    }

}