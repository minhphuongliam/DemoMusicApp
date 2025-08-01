package com.demo.musicapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.demo.musicapp.databinding.FragmentHomeSampleBinding
import com.demo.musicapp.navigation.AppNavigator
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
                // Chúng ta cần một action mới để quay về Auth
                appNavigator.navigateToAuthAndClearStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding =null
    }
}