package com.pi.ativas.teacher

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pi.ativas.databinding.FragmentPendingTaskStudentBinding
import com.pi.ativas.databinding.FragmentPendingTaskTeacherBinding

class PendingTaskTeacherFragment : Fragment() {

    private lateinit var binding: FragmentPendingTaskTeacherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentPendingTaskTeacherBinding.inflate(layoutInflater)
        return binding.root
    }
}