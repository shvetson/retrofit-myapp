package ru.shvets.myappretrofit.model.repository

import retrofit2.Call
import retrofit2.Response
import ru.shvets.myappretrofit.data.weather.ForecastResponse
import ru.shvets.myappretrofit.data.weather.WeatherResponse

/** @author Oleg Shvets shvetson@gmail.com on 2022-09-25 */

interface WeatherRepository {

    suspend fun getCurrentWeather(
        q: String,
        appid: String,
        units: String,
        lang: String
    ): Response<WeatherResponse>

    suspend fun getCurrentWeatherByCoords(
        lon: Float,
        lat: Float,
        appid: String,
        units: String,
        lang: String
    ): Response<WeatherResponse>

    suspend fun getForecastByCoords(
        lon: Float,
        lat: Float,
        appid: String,
        units: String,
        lang: String
    ): Call<ForecastResponse>
}