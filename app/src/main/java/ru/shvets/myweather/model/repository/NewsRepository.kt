package ru.shvets.myweather.model.repository

import retrofit2.Response
import ru.shvets.myweather.data.news.NewsResponse

/** @author Oleg Shvets shvetson@gmail.com on 2022-09-25 */

interface NewsRepository {

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Response<NewsResponse>

}