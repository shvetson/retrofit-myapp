package ru.shvets.myappretrofit.model.db.news.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ru.shvets.myappretrofit.data.news.Source
import kotlin.random.Random

@Entity(
    tableName = "sources",
    foreignKeys = [
        ForeignKey(
            entity = ArticleEntity::class,
            parentColumns = ["id"],
            childColumns = ["id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class SourceEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: String = Random.toString(),
    @ColumnInfo(name = "name")
    val name: String,
) {
    fun fromEntity(): Source = Source(
        id = id,
        name = name
    )

    companion object {
        fun toEntity(source: Source): SourceEntity = SourceEntity(
            id = "",
            name = source.name
        )
    }
}
