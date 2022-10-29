package com.pi.ativas.student.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pi.ativas.base.BaseFragment
import com.pi.ativas.databinding.FragmentViewTaskStudentBinding

class ViewTaskStudentFragment: BaseFragment() {

    private lateinit var binding: FragmentViewTaskStudentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewTaskStudentBinding.inflate(layoutInflater)

        return binding.root
    }
}