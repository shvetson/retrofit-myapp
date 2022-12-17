package ru.shvets.myappretrofit.view.root

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.shvets.myappretrofit.R
import ru.shvets.myappretrofit.databinding.FragmentRootBinding

class RootFragment : Fragment(R.layout.fragment_root) {
    private lateinit var _binding: FragmentRootBinding
    private val mBinding get() = _binding
//    private lateinit var actionBar: ActionBar

//    private lateinit var adapter: ViewPagerAdapter
//
//    private var fragmentList: ArrayList<Fragment> = arrayListOf()
//    private val tabTitles = mutableMapOf<String, Int>()

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRootBinding.inflate(inflater, container, false)
//        actionBar = (activity as AppCompatActivity).supportActionBar!!
//        actionBar.setTitle(R.string.title_weather_forecast)

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
        return mBinding.root
    }

}