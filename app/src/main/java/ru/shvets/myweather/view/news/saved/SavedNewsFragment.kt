package ru.shvets.myweather.view.news.saved

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.shvets.myweather.R
import ru.shvets.myweather.databinding.FragmentSavedNewsBinding
import ru.shvets.myweather.view.news.NewsViewModel

/** @author Oleg Shvets shvetson@gmail.com on 2022-09-25 */

class SavedNewsFragment: Fragment(R.layout.fragment_saved_news) {
    private lateinit var binding: FragmentSavedNewsBinding
    private val viewModel: NewsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSavedNewsBinding.bind(view)
    }
}