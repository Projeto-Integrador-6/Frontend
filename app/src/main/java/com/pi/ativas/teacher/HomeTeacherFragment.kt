package com.pi.ativas.teacher

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import com.pi.ativas.MainActivity
import com.pi.ativas.databinding.FragmentHomeTeacherBinding

class HomeTeacherFragment : Fragment() {

    private lateinit var binding: FragmentHomeTeacherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeTeacherBinding.inflate(layoutInflater)
        return binding.root
    }

}