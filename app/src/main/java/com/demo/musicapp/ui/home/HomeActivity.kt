package com.demo.musicapp.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
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
        // Fix bugg
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_home) as NavHostFragment
        val navController = navHostFragment.navController
        appNavigator.bind(navController)
    }

    override fun onDestroy() {
        super.onDestroy()
        //Fix Err
        // Không gọi appNavigator.unbind() tại đây.
        // Vì AppNavigator là singleton được chia sẻ toàn cục,
        // unbind() tại đây sẽ xóa NavController và gây lỗi null khi chuyển Activity sau logout.
        // Việc unbind nên được quản lý tại AuthActivity hoặc splash flow nơi điều hướng toàn bộ app.
    }
}