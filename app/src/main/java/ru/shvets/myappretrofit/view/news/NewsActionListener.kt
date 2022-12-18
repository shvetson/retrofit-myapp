package ru.shvets.myappretrofit.view.news

import ru.shvets.myappretrofit.data.news.Article

interface NewsActionListener {
    //Обработка кликов на элементе списка
    fun onItemClicked(article: Article)
    fun onLikeClicked(article: Article)
    fun onShareClicked(article: Article)
}