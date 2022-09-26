package com.pi.ativas.teacher

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pi.ativas.databinding.FragmentTaskHistoryTeacherBinding

class TaskHistoryTeacherFragment : Fragment() {
    private lateinit var binding: FragmentTaskHistoryTeacherBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentTaskHistoryTeacherBinding.inflate(layoutInflater)
        return binding.root
    }

}