package ru.shvets.myappretrofit.model.repository

import retrofit2.Response
import ru.shvets.myappretrofit.data.weather.WeatherResponse
import ru.shvets.myappretrofit.model.api.RetrofitInstance

/** @author Oleg Shvets shvetson@gmail.com on 2022-09-25 */

class WeatherRepositoryImpl : WeatherRepository {

    override suspend fun getCurrentWeather(
        q: String,
        appid: String,
        units: String,
        lang: String
    ): Response<WeatherResponse> {
        return RetrofitInstance.weatherApi.getCurrentWeather(q, appid, units, lang)
    }
}