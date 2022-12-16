package ru.shvets.myappretrofit.data.news

class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)
