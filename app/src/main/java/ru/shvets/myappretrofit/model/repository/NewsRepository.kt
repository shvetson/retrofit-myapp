package ru.shvets.myappretrofit.model.repository

import androidx.lifecycle.LiveData
import retrofit2.Response
import ru.shvets.myappretrofit.data.news.Article
import ru.shvets.myappretrofit.data.news.NewsResponse

interface NewsRepository {
    // Api
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Response<NewsResponse>
    suspend fun getSearchNews(searchQuery: String, pageNumber: Int): Response<NewsResponse>

    // Db
    suspend fun upsertArticle(article: Article): Long
    fun getAllSavedNewsArticles(): LiveData<List<Article>>
    suspend fun deleteArticle(article: Article)
    suspend fun deleteArticleById(articleId: Long)
}