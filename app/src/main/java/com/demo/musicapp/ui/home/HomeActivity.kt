package com.demo.musicapp.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.demo.musicapp.navigation.AppNavigator
import dagger.hilt.android.AndroidEntryPoint
import com.demo.musicapp.R
import com.demo.musicapp.databinding.ActivityHomeBinding
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var appNavigator: AppNavigator

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // "Gắn" NavController của HomeActivity vào Navigator singleton
        // Fix bugg
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_home) as NavHostFragment
        val navController = navHostFragment.navController
        appNavigator.bind(navController)

        //Gắn Bottom Navigation với NavController
        binding.bottomNavView.setupWithNavController(navController)
    }

}