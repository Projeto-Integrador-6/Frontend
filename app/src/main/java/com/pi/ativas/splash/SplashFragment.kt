package com.pi.ativas.splash

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.pi.ativas.R
import com.pi.ativas.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences("dataLogin", Context.MODE_PRIVATE)

        if (sharedPreferences.getBoolean("isLoggedUser", false)) {
            if (sharedPreferences.getBoolean("isStudent", true)) {
                findNavController().navigate(R.id.action_splashFragment_to_homeStudentFragment)
            } else {
                findNavController().navigate(R.id.action_splashFragment_to_homeTeacherFragment)
            }
        } else {
            Handler(Looper.myLooper()!!).postDelayed({
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            }, 2000)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(layoutInflater)
        return binding.root
    }
}