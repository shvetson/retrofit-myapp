package ru.shvets.myweather.view.news.search

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.shvets.myweather.MainActivity
import ru.shvets.myweather.R
import ru.shvets.myweather.databinding.FragmentSearchNewsBinding
import ru.shvets.myweather.util.Constants.Companion.NEWS_SEARCH_TIME_DELAY
import ru.shvets.myweather.util.Resource
import ru.shvets.myweather.util.hide
import ru.shvets.myweather.util.show
import ru.shvets.myweather.view.news.NewsAdapter
import ru.shvets.myweather.view.news.NewsViewModel

/** @author Oleg Shvets shvetson@gmail.com on 2022-09-25 */

class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {
    private lateinit var binding: FragmentSearchNewsBinding

    //    private val viewModel: NewsViewModel by viewModels()
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var viewModel: NewsViewModel
    private val loggingTag: String = SearchNewsFragment::class.java.simpleName.toString()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchNewsBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()

        var job: Job? = null
        binding.editTextSearch.addTextChangedListener {editable->
            job?.cancel()

            job = MainScope().launch {
                delay(NEWS_SEARCH_TIME_DELAY)
                editable?.let{
                    if (editable.toString().isNotEmpty())
                        viewModel.searchNews(editable.toString())
                }
            }
        }

        viewModel.searchNews.observe(viewLifecycleOwner, Observer { response ->

            when (response) {
                is Resource.Success -> {
                    binding.progressBarSearchNews.hide()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }
                is Resource.Error -> {
                    binding.progressBarSearchNews.hide()
                    response.message?.let { message ->
                        Log.e(loggingTag, "An error occurred: $message")
                    }
                }
                is Resource.Loading -> {
                    binding.progressBarSearchNews.show()
                }
            }
        })
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.recyclerViewSearchNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(context)

            val divider = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            addItemDecoration(divider)
        }
    }
}