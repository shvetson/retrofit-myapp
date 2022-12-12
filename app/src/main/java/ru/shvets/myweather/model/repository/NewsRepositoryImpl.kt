package ru.shvets.myweather.model.repository

import retrofit2.Response
import ru.shvets.myweather.data.news.NewsResponse
import ru.shvets.myweather.model.api.RetrofitInstance
import ru.shvets.myweather.model.db.ArticleDao

/** @author Oleg Shvets shvetson@gmail.com on 2022-09-25 */

class NewsRepositoryImpl(
    private val dao: ArticleDao
): NewsRepository {

    override suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Response<NewsResponse> {
        return RetrofitInstance.newsApi.getBreakingNews(countryCode, pageNumber)
    }

}