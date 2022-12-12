package ru.shvets.myweather.view.news.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.shvets.myweather.R
import ru.shvets.myweather.databinding.FragmentSearchNewsBinding
import ru.shvets.myweather.view.news.NewsViewModel

/** @author Oleg Shvets shvetson@gmail.com on 2022-09-25 */

class SearchNewsFragment: Fragment(R.layout.fragment_search_news) {
    private lateinit var binding: FragmentSearchNewsBinding
    private val viewModel: NewsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchNewsBinding.bind(view)


    }
}