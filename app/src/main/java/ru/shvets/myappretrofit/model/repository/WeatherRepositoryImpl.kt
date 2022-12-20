package ru.shvets.myappretrofit.model.repository

import retrofit2.Call
import retrofit2.Response
import ru.shvets.myappretrofit.data.weather.ForecastResponse
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

    override suspend fun getCurrentWeatherByCoords(
        lon: Float,
        lat: Float,
        appid: String,
        units: String,
        lang: String
    ): Response<WeatherResponse> {
        return RetrofitInstance.weatherApi.getCurrentWeatherByCoords(lon.toString(), lat.toString(), appid, units, lang)
    }

    override suspend fun getForecastByCoords(
        lon: Float,
        lat: Float,
        appid: String,
        units: String,
        lang: String
    ): Call<ForecastResponse> {
        return RetrofitInstance.weatherApi.getForecastByCoords(lon.toString(), lat.toString(), appid, units, lang)
    }
}