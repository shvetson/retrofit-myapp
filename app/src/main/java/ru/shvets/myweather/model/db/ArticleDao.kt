package ru.shvets.myweather.model.db

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.shvets.myweather.data.news.Article

/** @author Oleg Shvets shvetson@gmail.com on 2022-09-25 */

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article): Long

    @Query("SELECT * FROM articles")
    fun getAllArticle(): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)
}