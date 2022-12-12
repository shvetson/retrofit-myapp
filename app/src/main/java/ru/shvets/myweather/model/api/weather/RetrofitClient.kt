package ru.shvets.myweather.model.api.weather

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/** @author Oleg Shvets shvetson@gmail.com on 2022-09-25 */

object RetrofitClient {
    private var retrofit: Retrofit? = null

    fun getClient(baseUrl: String): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}