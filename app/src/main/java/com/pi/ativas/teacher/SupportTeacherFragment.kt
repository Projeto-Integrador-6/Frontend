package com.pi.ativas.teacher

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pi.ativas.R
import com.pi.ativas.databinding.FragmentSupportTeacherBinding

class SupportTeacherFragment : Fragment() {

    private lateinit var binding: FragmentSupportTeacherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSupportTeacherBinding.inflate(layoutInflater)
        return binding.root
    }
}