package ru.shvets.myweather.view.root

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(
    fragmentList: ArrayList<Fragment>,
    fm: FragmentManager,
    lifecycle: Lifecycle
): FragmentStateAdapter(fm, lifecycle){

    private val mFragmentList = fragmentList

    override fun getItemCount(): Int {
        return mFragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return mFragmentList[position]
    }
}