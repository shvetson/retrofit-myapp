package ru.shvets.myweather.model.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.shvets.myweather.model.api.news.NewsAPI
import ru.shvets.myweather.model.api.weather.WeatherAPI
import ru.shvets.myweather.util.Constants.Companion.NEWS_BASE_URL
import ru.shvets.myweather.util.Constants.Companion.WEATHER_BASE_URL

class RetrofitInstance {
    companion object {

        private val retrofit by lazy{
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
//                .build()
        }

        val newsApi: NewsAPI by lazy {
            retrofit.baseUrl(NEWS_BASE_URL).build().create(NewsAPI::class.java)
        }

        val weatherApi: WeatherAPI by lazy {
            retrofit.baseUrl(WEATHER_BASE_URL).build().create(WeatherAPI::class.java)
        }
    }
}