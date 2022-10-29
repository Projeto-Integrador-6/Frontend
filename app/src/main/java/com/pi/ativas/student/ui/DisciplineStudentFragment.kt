package com.pi.ativas.student.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pi.ativas.databinding.FragmentDisciplineStudentBinding

class DisciplineStudentFragment : Fragment() {
    private lateinit var binding: FragmentDisciplineStudentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDisciplineStudentBinding.inflate(layoutInflater)
        return binding.root
    }

}