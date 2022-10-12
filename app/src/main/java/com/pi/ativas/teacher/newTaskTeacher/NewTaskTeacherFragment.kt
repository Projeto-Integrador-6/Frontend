package com.pi.ativas.teacher.newTaskTeacher

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pi.ativas.databinding.FragmentNewTaskTeacherBinding

class NewTaskTeacherFragment : Fragment() {
    private lateinit var binding: FragmentNewTaskTeacherBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewTaskTeacherBinding.inflate(layoutInflater)
        return binding.root
    }
}