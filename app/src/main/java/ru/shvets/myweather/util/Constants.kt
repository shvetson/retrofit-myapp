package ru.shvets.myweather.util

class Constants {

    companion object {
        // Weather API
        const val WEATHER_BASE_URL = "https://api.openweathermap.org/"
        const val WEATHER_API_KEY = "ed99162a34a30298df0adee92fbc43b9"
        const val WEATHER_API_METRIC = "metric"
        const val WEATHER_API_LANG = "ru"

        // News API
        const val NEWS_BASE_URL = "https://newsapi.org/"
        const val NEWS_API_KEY = "9bba04552e0f4b44aea4fda453663f33"
        const val NEWS_COUNTRY = "ru"
        const val NEWS_SEARCH_TIME_DELAY = 500L

        // Database
        const val DATABASE_NAME = "db_helpful.db"
        const val TAG = "app_tag"
    }
}