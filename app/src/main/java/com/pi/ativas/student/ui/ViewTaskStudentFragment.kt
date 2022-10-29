package com.pi.ativas.student.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.pi.ativas.base.BaseFragment
import com.pi.ativas.databinding.FragmentViewTaskStudentBinding

class ViewTaskStudentFragment: BaseFragment() {

    private lateinit var binding: FragmentViewTaskStudentBinding
    private val viewTaskStudentFragmentArgs : ViewTaskStudentFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewTaskStudentBinding.inflate(layoutInflater)
        initViews()

        return binding.root
    }

    override fun initViews() {
        with(viewTaskStudentFragmentArgs.task){
            binding.titleTask.text = question_title
            binding.questionTask.text = question
            binding.btnMenu.setOnClickListener {
                Toast.makeText(requireContext(), "Click!", Toast.LENGTH_SHORT).show()
            }
            binding.btnPhoto.setOnClickListener {
                Toast.makeText(requireContext(), "Photo!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}