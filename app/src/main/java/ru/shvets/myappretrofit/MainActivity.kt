package ru.shvets.myappretrofit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.shvets.myappretrofit.databinding.ActivityMainBinding
import ru.shvets.myappretrofit.model.db.AppDatabase
import ru.shvets.myappretrofit.model.repository.NewsRepositoryImpl
import ru.shvets.myappretrofit.view.news.NewsViewModel
import ru.shvets.myappretrofit.view.news.NewsViewModelProviderFactory

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val mBinding get() = _binding

    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.fragment_splash)
//        setContentView(binding.root)

        val newsRepository = NewsRepositoryImpl(AppDatabase.invoke(this).articleDao())
        val viewModelProviderFactory = NewsViewModelProviderFactory(application, newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[NewsViewModel::class.java]

//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
//        val navController = navHostFragment.navController
//        mBinding.bottomNavigation.setupWithNavController(navController)


//        Handler(Looper.myLooper()!!).postDelayed({
//            mBinding?.let {
//                setContentView(it.root)
//                it.bottomNavigation.setupWithNavController(
//                    navController = it.fragmentContainer.findNavController()
//                )
//            }
//        }, 5000)

        CoroutineScope(Dispatchers.Main).launch {
            delay(5000)
            mBinding?.let {
                setContentView(it.root)
                it.bottomNavigation.setupWithNavController(
                    navController = it.fragmentContainer.findNavController()
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}