package ru.shvets.myweather.model.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.shvets.myweather.model.api.news.NewsApiService
import ru.shvets.myweather.util.Constants.Companion.NEWS_BASE_URL

/** @author Oleg Shvets shvetson@gmail.com on 2022-09-25 */

class RetrofitInstance {
    companion object {

        private val retrofit by lazy{
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            Retrofit.Builder()
                .baseUrl(NEWS_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val newsApi: NewsApiService by lazy {
            retrofit.create(NewsApiService::class.java)
        }
    }
}