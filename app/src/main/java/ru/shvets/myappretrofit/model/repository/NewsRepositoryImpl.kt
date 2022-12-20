package ru.shvets.myappretrofit.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import retrofit2.Response
import ru.shvets.myappretrofit.data.news.Article
import ru.shvets.myappretrofit.data.news.NewsResponse
import ru.shvets.myappretrofit.model.api.RetrofitInstance
import ru.shvets.myappretrofit.model.db.ArticleDao
import ru.shvets.myappretrofit.model.db.news.entity.ArticleEntity.Companion.toEntityFromModel

class NewsRepositoryImpl(
    private val dao: ArticleDao
) : NewsRepository {

    override suspend fun getBreakingNews(
        countryCode: String,
        pageNumber: Int
    ): Response<NewsResponse> {
        return RetrofitInstance.newsApi.getBreakingNews(countryCode, pageNumber)
    }

    override suspend fun getSearchNews(
        searchQuery: String,
        pageNumber: Int
    ): Response<NewsResponse> {
        return RetrofitInstance.newsApi.getSearchForNews(searchQuery, pageNumber)
    }

    override suspend fun upsertArticle(article: Article): Long {
        return dao.upsertArticle(toEntityFromModel(article))
    }

    override fun getAllSavedNewsArticles(): LiveData<List<Article>> {
        return Transformations.map(dao.getAllArticles()){entities->
            entities.map { entity ->
                entity.toModelFromEntity()
            }
        }
//        return dao.getAllArticles().map { list->
//            list.map {
//                it.toModelFromEntity()
//            }
//        }
    }

    override suspend fun deleteArticle(article: Article) {
        dao.deleteArticle(toEntityFromModel(article))
    }

    override suspend fun deleteArticleById(articleId: Long) {
        dao.deleteArticleById(articleId)
    }
}