package ru.shvets.myappretrofit.view.news.article

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import ru.shvets.myappretrofit.MainActivity
import ru.shvets.myappretrofit.R
import ru.shvets.myappretrofit.databinding.FragmentArticleBinding
import ru.shvets.myappretrofit.view.news.NewsViewModel

class ArticleFragment : Fragment(R.layout.fragment_article) {
    private var _binding: FragmentArticleBinding? = null
    private val mBinding get() = _binding

    //    private val viewModel: NewsViewModel by viewModels()
    private lateinit var viewModel: NewsViewModel
    private val args: ArticleFragmentArgs by navArgs()

    private val article
        get() = args.article

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentArticleBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel

        mBinding?.let {binding->
            binding.webView.apply {
                webViewClient = WebViewClient()
                article.url?.let { loadUrl(it) }
            }
        }

        mBinding?.let {
            it.fab.setOnClickListener {
                viewModel.saveArticle(article)
                Snackbar.make(view, "Article saved successfully!", Snackbar.LENGTH_SHORT).show()
            }

        }

    }
}