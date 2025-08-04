package com.demo.musicapp.ui.authentication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.demo.musicapp.databinding.ActivityAuthBinding
import com.demo.musicapp.navigation.AppNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.demo.musicapp.R
@AndroidEntryPoint
class AuthActivity: AppCompatActivity() {
    @Inject
    lateinit var appNavigator: AppNavigator

    private lateinit var binding: ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //lấy ra NavController mà NavHostFragment quản lý
        // ERR FIXXX: lấy tin cậy, tránh findFrag với NavHostFrag chưa được gắn view hoàn chỉnh
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_auth) as NavHostFragment
        val navController = navHostFragment.navController

        // gán cho navigator sủ dụng
        appNavigator.bind(navController)
    }

    // Hết vòng thì xóa navigator cho Auth (tránh tham chiếu var)
    override fun onDestroy() {
        super.onDestroy()

        appNavigator.unbind()
    }
}