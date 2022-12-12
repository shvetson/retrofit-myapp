package ru.shvets.myweather.view.root

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import ru.shvets.myweather.util.Constants
import ru.shvets.myweather.R
import ru.shvets.myweather.databinding.FragmentRootBinding
import ru.shvets.myweather.view.news.breaking.BreakingNewsFragment
import ru.shvets.myweather.view.weather.WeatherFragment

class RootFragment : Fragment(R.layout.fragment_root) {
    private lateinit var binding: FragmentRootBinding
    private lateinit var adapter: ViewPagerAdapter

    private var fragmentList: ArrayList<Fragment> = arrayListOf()
    private val tabTitles = mutableMapOf<String, Int>()

//    private var _context: Context? = null
//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        _context = context
//    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        fragmentList.add(WeatherFragment())
//        fragmentList.add(BreakingNewsFragment())
//
//        tabTitles[getString(R.string.title_tab_weather)] = R.drawable.ic_weather
//        tabTitles[getString(R.string.title_tab_news)] = R.drawable.ic_news
//    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putInt(Constants.POSITION, binding.tabLayout.selectedTabPosition)
//    }
//
//    override fun onViewStateRestored(savedInstanceState: Bundle?) {
//        super.onViewStateRestored(savedInstanceState)
//        savedInstanceState?.getInt(Constants.POSITION)?.let {
//            binding.viewPager.currentItem = it
//        }
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentRootBinding.inflate(inflater, container, false)
//
//        val titles = ArrayList(tabTitles.keys)
//        val icons = ArrayList(tabTitles.values)
//
//        adapter = ViewPagerAdapter(
//            fragmentList,
//            requireActivity().supportFragmentManager,
//            lifecycle
//        )
//
//        with(binding) {
//            viewPager.adapter = adapter
//            // это необходимо обязательно для того, чтобы управлять цветами tabLayout !!!
//            tabLayout.tabIconTint = null
//            TabLayoutMediator(tabLayout, viewPager) { tab, pos ->
//                tab.text = titles[pos]
//                tab.setIcon(icons[pos])
//            }.attach()
//        }
//        return binding.root
//    }
}