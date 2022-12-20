package ru.shvets.myappretrofit.model.db.news.entity

import androidx.room.*

@Entity(
    tableName = "likes",
    indices = [
        Index("article_id")
    ],
    foreignKeys = [
        ForeignKey(
            entity = ArticleEntity::class,
            parentColumns = ["id"],
            childColumns = ["article_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class LikeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "article_id")
    val articleId: Long,
    @ColumnInfo(name = "liked")
    val liked: Boolean = true
)