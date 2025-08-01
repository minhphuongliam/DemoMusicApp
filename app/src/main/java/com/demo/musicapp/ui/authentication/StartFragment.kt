package com.demo.musicapp.ui.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.demo.musicapp.databinding.FragmentStartBinding
import com.demo.musicapp.navigation.AppNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StartFragment : Fragment() {

    @Inject
    lateinit var appNavigator: AppNavigator

    private var _binding: FragmentStartBinding?= null
    private val binding = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStartBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //gán sự kiện button
        setupListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setupListeners()
    {
        //gọi -> signup
        binding.buttonStartSignUpButton.setOnClickListener {
            appNavigator.openStartToSignup()
        }
        // gọi -> login
        binding.buttonStartLoginButton.setOnClickListener{
            appNavigator.openStartToLogin()
        }

    }

}