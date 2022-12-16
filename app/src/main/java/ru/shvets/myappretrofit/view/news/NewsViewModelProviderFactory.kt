package ru.shvets.myappretrofit.view.news

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.shvets.myappretrofit.model.repository.NewsRepository

class NewsViewModelProviderFactory(
    val app: Application,
    private val repo: NewsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(app, repo) as T
    }
}