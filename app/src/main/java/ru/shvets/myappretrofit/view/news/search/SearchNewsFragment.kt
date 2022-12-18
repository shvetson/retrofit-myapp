package ru.shvets.myappretrofit.view.news.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.shvets.myappretrofit.MainActivity
import ru.shvets.myappretrofit.R
import ru.shvets.myappretrofit.data.news.Article
import ru.shvets.myappretrofit.databinding.FragmentSearchNewsBinding
import ru.shvets.myappretrofit.util.Constants
import ru.shvets.myappretrofit.util.Constants.Companion.NEWS_SEARCH_TIME_DELAY
import ru.shvets.myappretrofit.util.Resource
import ru.shvets.myappretrofit.util.hide
import ru.shvets.myappretrofit.util.show
import ru.shvets.myappretrofit.view.news.NewsActionListener
import ru.shvets.myappretrofit.view.news.NewsAdapter
import ru.shvets.myappretrofit.view.news.NewsViewModel

class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {
    private lateinit var _binding: FragmentSearchNewsBinding
    private val mBinding get() = _binding
//    private lateinit var actionBar: ActionBar

    //    private val viewModel: NewsViewModel by viewModels()
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var viewModel: NewsViewModel
    private val loggingTag: String = SearchNewsFragment::class.java.simpleName.toString()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchNewsBinding.inflate(layoutInflater, container, false)
//        actionBar = (activity as AppCompatActivity).supportActionBar!!
//        actionBar.setTitle(R.string.title_weather)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()

        var job: Job? = null
        mBinding.editTextSearch.addTextChangedListener {editable->
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
                    mBinding.progressBarSearchNews.hide()
                    isLoading = false
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles.toList())

                        val totalPages = newsResponse.totalResults / Constants.QUERY_PAGE_SIZE + 2
                        isLastPage = viewModel.searchNewsPage == totalPages

                        if (isLastPage) {
                            mBinding.recyclerViewSearchNews.setPadding(0, 0, 0, 0)
                        }
                    }
                }
                is Resource.Error -> {
                    mBinding.progressBarSearchNews.hide()
                    isLoading = false
                    response.message?.let { message ->
                        Toast.makeText(activity, "An error occurred: $message", Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading -> {
                    mBinding.progressBarSearchNews.show()
                    isLoading = true
                }
            }
        })
    }

    // Реализация скроллинга списка RecyclerView для плавного отображения и загрузки инфо по необходимости
    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    private val scrollListener = object: RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAngNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisiblePosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisiblePosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= Constants.QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAngNotLastPage && isAtLastItem && isNotAtBeginning && isTotalMoreThanVisible && isScrolling

            if (shouldPaginate) {
                viewModel.searchNews(mBinding.editTextSearch.toString())
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true

            }
        }
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter(object : NewsActionListener{
            override fun onItemClicked(article: Article) {
            }

            override fun onLikeClicked(article: Article) {
            }

            override fun onShareClicked(article: Article) {
            }

        })

        mBinding.recyclerViewSearchNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(context)

            val divider = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            addItemDecoration(divider)

            addOnScrollListener(this@SearchNewsFragment.scrollListener)
        }
    }
}