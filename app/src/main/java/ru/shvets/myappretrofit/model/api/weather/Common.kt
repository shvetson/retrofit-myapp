package ru.shvets.myappretrofit.model.api.weather

import ru.shvets.myappretrofit.util.Constants.Companion.WEATHER_BASE_URL

/** @author Oleg Shvets shvetson@gmail.com on 2022-09-25 */

object Common {
    val weatherAPI: WeatherAPI
        get() = RetrofitClient.getClient(WEATHER_BASE_URL).create(WeatherAPI::class.java)
}