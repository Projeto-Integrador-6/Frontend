package com.pi.ativas.student.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.pi.ativas.databinding.FragmentHomeStudentBinding
import com.pi.ativas.student.viewmodel.HomeStudentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeStudentFragment : Fragment() {

    private lateinit var binding: FragmentHomeStudentBinding
    private val viewModel: HomeStudentViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeStudentBinding.inflate(layoutInflater)
        return binding.root
    }

}