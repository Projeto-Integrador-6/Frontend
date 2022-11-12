package com.pi.ativas.student.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pi.ativas.databinding.FragmentTaskHistoryStudentBinding

class TaskHistoryStudentFragment : Fragment() {
    private lateinit var binding: FragmentTaskHistoryStudentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentTaskHistoryStudentBinding.inflate(layoutInflater)
        return binding.root
    }
}