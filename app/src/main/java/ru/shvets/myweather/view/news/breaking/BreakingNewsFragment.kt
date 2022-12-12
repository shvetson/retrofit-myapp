package ru.shvets.myweather.view.news.breaking

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ru.shvets.myweather.MainActivity
import ru.shvets.myweather.R
import ru.shvets.myweather.databinding.FragmentBreakingNewsBinding
import ru.shvets.myweather.util.Resource
import ru.shvets.myweather.util.hide
import ru.shvets.myweather.util.show
import ru.shvets.myweather.view.news.NewsAdapter
import ru.shvets.myweather.view.news.NewsViewModel

class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {
    private lateinit var binding: FragmentBreakingNewsBinding
    //    private val viewModel: NewsViewModel by viewModels()
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var viewModel: NewsViewModel
    private val loggingTag: String = BreakingNewsFragment::class.java.simpleName.toString()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBreakingNewsBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel

        setupRecyclerView()
        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response ->

            when (response) {
                is Resource.Success -> {
                    binding.progressBarNews.hide()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }
                is Resource.Error -> {
                    binding.progressBarNews.hide()
                    response.message?.let { message ->
                        Log.e(loggingTag, "An error occurred: $message")
                    }
                }
                is Resource.Loading -> {
                    binding.progressBarNews.show()
                }
            }
        })
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.recyclerViewBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(context)

            val divider = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            addItemDecoration(divider)
        }
    }
}