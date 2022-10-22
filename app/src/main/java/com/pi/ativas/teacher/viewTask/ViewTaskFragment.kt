package com.pi.ativas.teacher.viewTask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pi.ativas.base.BaseFragment
import com.pi.ativas.databinding.FragmentViewTaskBinding

class ViewTaskFragment: BaseFragment() {


    private lateinit var binding: FragmentViewTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentViewTaskBinding.inflate(layoutInflater)

        return binding.root
    }
}