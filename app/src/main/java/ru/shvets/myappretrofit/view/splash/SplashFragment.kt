package ru.shvets.myappretrofit.view.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.shvets.myappretrofit.R
import ru.shvets.myappretrofit.databinding.FragmentSplashBinding

class SplashFragment : Fragment(R.layout.fragment_splash) {
    private var _binding: FragmentSplashBinding? = null
    private val mBinding get() = _binding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSplashBinding.bind(view)
    }
}