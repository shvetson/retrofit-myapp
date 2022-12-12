package ru.shvets.myweather.model.repository

import retrofit2.Response
import ru.shvets.myweather.data.news.NewsResponse
import ru.shvets.myweather.model.api.RetrofitInstance
import ru.shvets.myweather.model.db.ArticleDao

class NewsRepositoryImpl(
    private val dao: ArticleDao
) : NewsRepository {

    override suspend fun getBreakingNews(
        countryCode: String,
        pageNumber: Int
    ): Response<NewsResponse> {
        return RetrofitInstance.newsApi.getBreakingNews(countryCode, pageNumber)
    }

    override suspend fun searchNews(
        searchQuery: String,
        pageNumber: Int
    ): Response<NewsResponse> {
        return RetrofitInstance.newsApi.searchForNews(searchQuery, pageNumber)
    }
}