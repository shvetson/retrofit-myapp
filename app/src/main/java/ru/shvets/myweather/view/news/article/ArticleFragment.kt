package ru.shvets.myweather.view.news.article

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.shvets.myweather.R
import ru.shvets.myweather.databinding.FragmentArticleBinding
import ru.shvets.myweather.view.news.NewsViewModel

/** @author Oleg Shvets shvetson@gmail.com on 2022-09-25 */

class ArticleFragment: Fragment(R.layout.fragment_article) {
    private lateinit var binding: FragmentArticleBinding
    private val viewModel: NewsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArticleBinding.bind(view)


    }
}