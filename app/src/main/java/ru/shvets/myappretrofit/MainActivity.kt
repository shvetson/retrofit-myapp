package ru.shvets.myappretrofit

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.shvets.myappretrofit.databinding.ActivityMainBinding
import ru.shvets.myappretrofit.model.db.AppDatabase
import ru.shvets.myappretrofit.model.repository.NewsRepositoryImpl
import ru.shvets.myappretrofit.util.Extensions.toast
import ru.shvets.myappretrofit.util.FirebaseUtils.firebaseAuth
import ru.shvets.myappretrofit.view.news.NewsViewModel
import ru.shvets.myappretrofit.view.news.NewsViewModelProviderFactory

class MainActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainBinding
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
            delay(2000)
            mBinding.let {
                setContentView(it.root)
                it.bottomNavigation.setupWithNavController(
                    navController = it.fragmentContainer.findNavController()
                )
            }
        }

        setSupportActionBar(mBinding.toolbar)

        val navController = (supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment).navController
        val config = AppBarConfiguration(navController.graph)
//        Не будет стрелочки домой
//        val config = AppBarConfiguration(setOf(
//            R.id.weatherFragment,
//            R.id.breakingNewsFragment,
//            R.id.savedNewsFragment,
//            R.id.searchNewsFragment
//        ))
        mBinding.toolbar.setupWithNavController(navController, config)
        mBinding.bottomNavigation.setupWithNavController(navController)

//        Позволяет вывести в верхнем меню иконку и название приложение (возврат - стрелки нет)
//        NavigationUI.navigateUp(navController, config)

//        NavigationUI.setupActionBarWithNavController(this, navController, config)
//        NavigationUI.setupWithNavController(mBinding.bottomNavigation, navController)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)

        val item: MenuItem = menu.findItem(R.id.action_login)
        item.isEnabled = false
        item.isVisible = false

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
//            R.id.action_login->{}
            R.id.action_logout -> {
                val listener = DialogInterface.OnClickListener { _, which ->
                    if (which == DialogInterface.BUTTON_POSITIVE) {

                        firebaseAuth.signOut()
                        toast("Signed out")

                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }
                }
                val dialog = AlertDialog.Builder(this)
                    .setTitle(R.string.alert_dialog_logout_title)
                    .setMessage(R.string.alert_dialog_logout_message)
                    .setPositiveButton(R.string.alert_dialog_logout_positive_button, listener)
                    .setNegativeButton(R.string.alert_dialog_logout_negative_button, listener)
                    .create()
                dialog.show()
            }
//            R.id.filter_graph -> item.onNavDestinationSelected(findNavController(R.id.fragment_container))
        }
        return super.onOptionsItemSelected(item)
    }
}