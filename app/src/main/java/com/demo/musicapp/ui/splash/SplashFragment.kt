package com.demo.musicapp.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.demo.musicapp.databinding.FragmentLoginBinding
import com.demo.musicapp.databinding.FragmentSplashBinding
import com.demo.musicapp.navigation.AppNavigator
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {
    @Inject
    lateinit var appNavigator : AppNavigator

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SplashFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //quan sát event navigate
        viewModel.navigationEvent.observe(viewLifecycleOwner){ event ->
            when(event){
                is SplashNavigationEvent.NavigateToHome ->{
                    appNavigator.navigateToHome()
                }
                is SplashNavigationEvent.NavigateToLoginWithError ->
                {
                    // Hiển thị thông báo lỗi trước khi chuyển màn
                    Toast.makeText(context,  getString(event.messageId), Toast.LENGTH_LONG).show()
                    appNavigator.openSplashToLogin()
                }
                is SplashNavigationEvent.NavigateToStart ->{
                    appNavigator.openSplashToStart()
                }
            }
        }
        //gọi chuyển màn hình tiếp
        viewModel.deviceNextScreen()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}