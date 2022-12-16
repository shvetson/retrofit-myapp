package ru.shvets.myappretrofit.model.db.news.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import ru.shvets.myappretrofit.data.news.Article
import ru.shvets.myappretrofit.data.news.Source

@Entity(
    tableName = "articles",
    indices = [
        Index("content")
    ]
)
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long,
    @ColumnInfo(name = "author")
    val author: String,
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "publishedAt")
    val publishedAt: String,
    @ColumnInfo(name = "source")
    val source: Source,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "urlToImage")
    val urlToImage: String
) {
    fun toModelFromEntity(): Article = Article(
        id = id,
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = source,
        title = title,
        url = url,
        urlToImage = urlToImage
    )

    companion object {
        fun toEntityFromModel(article: Article): ArticleEntity =
            ArticleEntity(
                id = 0L,
                author = article.author ?: "",
                content = article.content ?: "",
                description = article.description ?: "",
                publishedAt = article.publishedAt ?: "",
                source = article.source ?: Source("", ""),
                title = article.title ?: "",
                url = article.url ?: "",
                urlToImage = article.urlToImage ?: ""
            )
    }
}