package com.demo.musicapp.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.demo.musicapp.navigation.AppNavigator
import dagger.hilt.android.AndroidEntryPoint
import com.demo.musicapp.R
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var appNavigator: AppNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // "Gắn" NavController của HomeActivity vào Navigator singleton
        val navController = findNavController(R.id.nav_host_fragment_home)
        appNavigator.bind(navController)
    }

    override fun onDestroy() {
        super.onDestroy()
        // "Tháo" NavController ra khi Activity bị hủy
        appNavigator.unbind()
    }
}