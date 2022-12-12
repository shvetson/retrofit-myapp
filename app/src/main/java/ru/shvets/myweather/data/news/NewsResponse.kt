package ru.shvets.myweather.data.news

class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)
