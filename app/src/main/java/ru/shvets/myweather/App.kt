package ru.shvets.myweather

import android.app.Application
import ru.shvets.myweather.model.db.AppDatabase
import ru.shvets.myweather.model.repository.NewsRepository
import ru.shvets.myweather.model.repository.NewsRepositoryImpl
import ru.shvets.myweather.model.repository.WeatherRepository
import ru.shvets.myweather.model.repository.WeatherRepositoryImpl
import ru.shvets.myweather.util.Constants.Companion.DATABASE_NAME

/** @author Oleg Shvets shvetson@gmail.com on 2022-09-25 */

class App: Application() {
    private lateinit var database: AppDatabase
    lateinit var weatherRepository: WeatherRepository
    lateinit var newsRepository: NewsRepository

    override fun onCreate() {
        super.onCreate()

        database = AppDatabase.invoke(applicationContext)
//        database = AppDatabase.buildDatabase(applicationContext, DATABASE_NAME)


        weatherRepository = WeatherRepositoryImpl()
        newsRepository = NewsRepositoryImpl(database.articleDao())
    }
}