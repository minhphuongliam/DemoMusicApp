package com.demo.musicapp.ui.home.home_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.demo.musicapp.R
import com.demo.musicapp.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.random.Random

class HomeFragment: Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // khởi tạo , gắn adapter
        val pageAdapter = HomeViewPagerAdapter(this)
        binding.viewPager.adapter = pageAdapter
        //Kết nối viewpager với tabLayout
        //TLMediator tự xử lý đồng bộ, ovr onConfigureTab
        TabLayoutMediator(binding.tabLayout, binding.viewPager){ tab, position ->
            //tiêu đề từng tab
            tab.text = getTabTitle(position)
        }.attach()
    }

    private fun getTabTitle(position: Int): String {
        return when (position) {
            0 -> getString(R.string.tab_track)
            1 -> getString(R.string.tab_album)
            2 -> getString(R.string.tab_artist)
            else -> ""
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}