package ru.shvets.myappretrofit.model.db

import android.content.Context
import androidx.room.*
import ru.shvets.myappretrofit.model.db.news.entity.ArticleEntity
import ru.shvets.myappretrofit.util.Constants.Companion.DATABASE_NAME

@Database(
    entities = [ArticleEntity::class],
    version = 1,
    exportSchema = true
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