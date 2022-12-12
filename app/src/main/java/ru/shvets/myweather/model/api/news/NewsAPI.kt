package ru.shvets.myweather.model.api.news

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.shvets.myweather.data.news.NewsResponse
import ru.shvets.myweather.util.Constants.Companion.NEWS_API_KEY
import ru.shvets.myweather.util.Constants.Companion.NEWS_COUNTRY

interface NewsAPI {

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country") countryCode: String = NEWS_COUNTRY,
        @Query("page") pageNumber: Int = 1,
        @Query("apiKey") apiKey: String = NEWS_API_KEY
    ): Response<NewsResponse>

    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("q") searchQuery: String,
        @Query("page") pageNumber: Int = 1,
        @Query("apiKey") apiKey: String = NEWS_API_KEY
    ): Response<NewsResponse>
}