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
import com.pi.ativas.MainActivity
import com.pi.ativas.R
import com.pi.ativas.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences("dataLogin", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initViews()
        binding = FragmentSplashBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun initViews(){
        if (sharedPreferences.getBoolean("isLoggedUser", false)) {
            with(sharedPreferences) {
                getString("userName", "null")?.let { userName ->
                    getString("userEmail", "null")?.let { userEmail ->
                        (activity as MainActivity).setNavHeader(userName, userEmail)
                    }
                }
            }
            if (sharedPreferences.getBoolean("isStudent", true)) {
                (activity as MainActivity).getDrawerStudent()
                findNavController().navigate(R.id.action_splashFragment_to_homeStudentFragment)
            } else {
                (activity as MainActivity).getDrawerTeatcher()
                findNavController().navigate(R.id.action_splashFragment_to_homeTeacherFragment)
            }
        } else {
            Handler(Looper.myLooper()!!).postDelayed({
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            }, 2000)
        }
    }
}