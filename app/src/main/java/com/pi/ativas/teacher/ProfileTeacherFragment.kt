package com.pi.ativas.teacher

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pi.ativas.databinding.FragmentProfileTeacherBinding

class ProfileTeacherFragment : Fragment() {

   private lateinit var binding: FragmentProfileTeacherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileTeacherBinding.inflate(layoutInflater)
        return binding.root
    }


}