package com.pi.ativas.student

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pi.ativas.databinding.FragmentUseTermsStudentBinding

class UseTermsStudentFragment : Fragment() {
    private lateinit var binding : FragmentUseTermsStudentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUseTermsStudentBinding.inflate(layoutInflater)
        return binding.root
    }

}