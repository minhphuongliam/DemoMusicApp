package com.demo.musicapp.ui.home.home_screen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.demo.musicapp.databinding.FragmentHomeSampleBinding
import com.demo.musicapp.navigation.AppNavigator
import com.demo.musicapp.ui.authentication.AuthActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeSampleFragment : Fragment(){
    @Inject
    lateinit var appNavigator: AppNavigator

    private var _binding: FragmentHomeSampleBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeSampleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLogout.setOnClickListener {
            viewModel.onLogoutClicked()
        }

        viewModel.navigationEvent.observe(viewLifecycleOwner) { event ->
            if (event is HomeNavigationEvent.NavigateToAuth) {
                // Cần quay về Splash, nên mở lại AuthActivity với Intent
                requireActivity().apply {
                    startActivity(
                        Intent(this, AuthActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }
                    )
                    finish() // Kết thúc HomeActivity
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding =null
    }
}