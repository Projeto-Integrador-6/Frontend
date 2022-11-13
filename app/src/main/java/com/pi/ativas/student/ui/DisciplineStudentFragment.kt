package com.pi.ativas.student.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pi.ativas.data.bodys.RequestTaskBody
import com.pi.ativas.databinding.FragmentDisciplineStudentBinding
import com.pi.ativas.student.viewmodel.DisciplineStudentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DisciplineStudentFragment : Fragment() {
    private lateinit var binding: FragmentDisciplineStudentBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val viewModel: DisciplineStudentViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences("dataLogin", Context.MODE_PRIVATE)

        with(sharedPreferences) {
            getString("email", "")?.let { email ->
                getString("password", "")?.let { password ->
                    getString("token", "")?.let { token ->
                    }
                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDisciplineStudentBinding.inflate(layoutInflater)
        return binding.root
    }

}