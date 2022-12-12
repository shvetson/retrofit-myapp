package ru.shvets.myweather.model.db

import android.content.Context
import androidx.room.*
import ru.shvets.myweather.data.news.Article
import ru.shvets.myweather.util.Constants.Companion.DATABASE_NAME

/** @author Oleg Shvets shvetson@gmail.com on 2022-09-25 */

@Database(
    entities = [Article::class],
    version = 1
)

@TypeConverters(Converters::class)

abstract class AppDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME
            ).build()
    }
}

//abstract class AppDatabase : RoomDatabase() {
//    abstract fun recipeDao(): RecipeDao
//    abstract fun categoryDao(): CategoryDao
//    abstract fun stepDao(): StepDao
//
//    companion object {
//        fun buildDatabase(context: Context, dbName: String): AppDatabase {
//            return Room.databaseBuilder(context, AppDatabase::class.java, dbName)
//                .allowMainThreadQueries()
//                .createFromAsset("database/recipes.db")
//                .build()
//        }
//    }
//}