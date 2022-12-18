package ru.shvets.myappretrofit.view.news.breaking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.shvets.myappretrofit.MainActivity
import ru.shvets.myappretrofit.R
import ru.shvets.myappretrofit.data.news.Article
import ru.shvets.myappretrofit.databinding.FragmentBreakingNewsBinding
import ru.shvets.myappretrofit.util.Constants.Companion.NEWS_COUNTRY
import ru.shvets.myappretrofit.util.Constants.Companion.QUERY_PAGE_SIZE
import ru.shvets.myappretrofit.util.Resource
import ru.shvets.myappretrofit.util.hide
import ru.shvets.myappretrofit.util.show
import ru.shvets.myappretrofit.view.news.NewsActionListener
import ru.shvets.myappretrofit.view.news.NewsAdapter
import ru.shvets.myappretrofit.view.news.NewsViewModel

class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {
    private lateinit var _binding: FragmentBreakingNewsBinding
    private val mBinding get() = _binding
//    private lateinit var actionBar: ActionBar

    //    private val viewModel: NewsViewModel by viewModels()
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBreakingNewsBinding.inflate(layoutInflater, container, false)
//        actionBar = (activity as AppCompatActivity).supportActionBar!!
//        actionBar.setTitle(R.string.title_weather)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()

        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response ->

            when (response) {
                is Resource.Success -> {
                    mBinding.progressBarNews.hide()
                    isLoading = false
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles.toList())

                        val totalPages = newsResponse.totalResults / QUERY_PAGE_SIZE + 2
                        isLastPage = viewModel.breakingNewsPage == totalPages

                        if (isLastPage) {
                            mBinding.recyclerViewBreakingNews.setPadding(0, 0, 0, 0)
                        }
                    }
                }
                is Resource.Error -> {
                    mBinding.progressBarNews.hide()
                    isLoading = false
                    response.message?.let { message ->
                        Toast.makeText(activity, "An error occurred: $message", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                is Resource.Loading -> {
                    mBinding.progressBarNews.show()
                    isLoading = true
                }
            }
        })
    }

    // Реализация скроллинга списка RecyclerView для плавного отображения и загрузки инфо по необходимости
    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAngNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisiblePosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisiblePosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate =
                isNotLoadingAngNotLastPage && isAtLastItem && isNotAtBeginning && isTotalMoreThanVisible && isScrolling

            if (shouldPaginate) {
                viewModel.getBreakingNews(NEWS_COUNTRY)
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

        newsAdapter = NewsAdapter(object : NewsActionListener {
            override fun onLikeClicked(article: Article) {
//                newsViewModel.updateLiked(article)
            }

            override fun onShareClicked(article: Article) {

            }

            override fun onItemClicked(article: Article) {
                val direction = BreakingNewsFragmentDirections
                    .actionBreakingNewsFragmentToArticleDetailsFragment(article)
                findNavController().navigate(direction)
            }
        })

        mBinding.recyclerViewBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(context)

            val divider = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            addItemDecoration(divider)

            addOnScrollListener(this@BreakingNewsFragment.scrollListener)
        }
    }
}