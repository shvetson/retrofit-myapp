package ru.shvets.myweather.model.db

import androidx.room.TypeConverter
import ru.shvets.myweather.data.news.Source

class Converters {

    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}