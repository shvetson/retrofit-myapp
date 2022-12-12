package ru.shvets.myweather.model.db

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.shvets.myweather.model.db.news.entity.ArticleEntity

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertArticle(article: ArticleEntity): Long

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<ArticleEntity>>

    @Delete
    suspend fun deleteArticle(article: ArticleEntity)
}