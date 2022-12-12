package ru.shvets.myweather.view.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import ru.shvets.myweather.data.news.NewsResponse
import ru.shvets.myweather.model.repository.NewsRepository
import ru.shvets.myweather.util.Constants.Companion.NEWS_COUNTRY
import ru.shvets.myweather.util.Resource

//class NewsViewModel(
//    application: Application
//) : AndroidViewModel(application) {
//
//    private val repo = (application.applicationContext as App).newsRepository

class NewsViewModel(
    private val repo: NewsRepository
) : ViewModel() {

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    private var breakingNewsPage = 1

    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    private var searchNewsPage = 1

    init {
        getBreakingNews(NEWS_COUNTRY)
    }

    private fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = repo.getBreakingNews(countryCode, breakingNewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


    fun searchNews(searchQuery: String) {
        viewModelScope.launch {
            searchNews.postValue(Resource.Loading())
            val response = repo.searchNews(searchQuery, searchNewsPage)
            searchNews.postValue(handleSearchNewsResponse(response))
        }
    }


    private fun handleSearchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}