package ru.shvets.myweather.model.repository

import retrofit2.Call
import ru.shvets.myweather.data.weather.Weather
import ru.shvets.myweather.model.api.Common

/** @author Oleg Shvets shvetson@gmail.com on 2022-09-25 */

class WeatherRepositoryImpl : WeatherRepository {
    private val weatherApi = Common.weatherApiService

    override fun getCurrentWeather(
        q: String,
        appid: String,
        units: String,
        lang: String
    ): Call<Weather> {
        return weatherApi.getCurrentWeather(q, appid, units, lang)
    }

}