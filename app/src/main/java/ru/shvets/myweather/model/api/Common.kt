package ru.shvets.myweather.model.api

import ru.shvets.myweather.util.Constants
import ru.shvets.myweather.util.Constants.Companion.WEATHER_BASE_URL

/** @author Oleg Shvets shvetson@gmail.com on 2022-09-25 */

object Common {
    val weatherApiService: WeatherApiService
        get() = RetrofitClient.getClient(WEATHER_BASE_URL).create(WeatherApiService::class.java)
}