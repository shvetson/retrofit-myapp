package ru.shvets.myweather.view.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.shvets.myweather.model.repository.NewsRepository

class NewsViewModelProviderFactory(
    private val repo: NewsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(repo) as T
    }
}