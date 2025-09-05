package com.demo.musicapp.ui.home.home_screen

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.demo.musicapp.ui.home.shared.AlbumListFragment
import com.demo.musicapp.ui.home.shared.ArtistListFragment
import com.demo.musicapp.ui.home.shared.TrackListFragment

//Số tab
private const val NUM_TABS = 3

class HomeViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return NUM_TABS
    }

    //trả về Fragment tương ứng với vị trí
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> TrackListFragment()
            1 -> AlbumListFragment()
            2 -> ArtistListFragment()
            else -> throw IllegalStateException("Invalid position: $position")
        }
    }
}