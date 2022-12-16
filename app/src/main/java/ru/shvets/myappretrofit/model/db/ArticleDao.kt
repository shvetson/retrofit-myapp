package ru.shvets.myappretrofit.model.db

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.shvets.myappretrofit.model.db.news.entity.ArticleEntity

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertArticle(article: ArticleEntity): Long

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<ArticleEntity>>

    @Delete
    suspend fun deleteArticle(article: ArticleEntity)

    @Query("DELETE FROM articles WHERE id = :articleId")
    suspend fun deleteArticleById(articleId: Long)
}