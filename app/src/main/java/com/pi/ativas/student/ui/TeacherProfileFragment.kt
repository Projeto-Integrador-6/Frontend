package com.pi.ativas.student.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.pi.ativas.base.BaseFragment
import com.pi.ativas.databinding.FragmentTeacherProfileBinding

class TeacherProfileFragment: BaseFragment() {

    private lateinit var binding: FragmentTeacherProfileBinding
    private val teacherProfileFragmentArgs: TeacherProfileFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTeacherProfileBinding.inflate(layoutInflater)
        initViews()
        return binding.root
    }

    override fun initViews() {
        super.initViews()
        val teacher = teacherProfileFragmentArgs.teacher
        with(binding){
            txtSeusDadosNome.setText(teacher.name)
            txtSeusDadosEmail.setText(teacher.email)
            txtSeusDadosContato.setText(teacher.registration)
            txtSeusDadosAniversario.setText(teacher.discipline_name)
            txtSeusDadosCurriculo.setText(teacher.lattes)
        }
    }
}