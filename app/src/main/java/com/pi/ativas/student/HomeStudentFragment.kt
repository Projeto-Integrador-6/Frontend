package com.pi.ativas.student

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.*
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.pi.ativas.MainActivity
import com.pi.ativas.R
import com.pi.ativas.databinding.FragmentHomeStudentBinding
import com.pi.ativas.util.LoadToggle

class HomeStudentFragment : Fragment() {

    private lateinit var binding: FragmentHomeStudentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeStudentBinding.inflate(layoutInflater)
        return binding.root
    }

}