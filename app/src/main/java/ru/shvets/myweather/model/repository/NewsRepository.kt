package ru.shvets.myweather.model.repository

import retrofit2.Response
import ru.shvets.myweather.data.news.NewsResponse

interface NewsRepository {

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Response<NewsResponse>
    suspend fun searchNews(searchQuery: String, pageNumber: Int): Response<NewsResponse>

}