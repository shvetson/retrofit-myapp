package ru.shvets.myappretrofit

import android.app.Application
import ru.shvets.myappretrofit.model.db.AppDatabase
import ru.shvets.myappretrofit.model.repository.NewsRepository
import ru.shvets.myappretrofit.model.repository.NewsRepositoryImpl
import ru.shvets.myappretrofit.model.repository.WeatherRepository
import ru.shvets.myappretrofit.model.repository.WeatherRepositoryImpl

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