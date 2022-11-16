package com.pi.ativas.teacher.studentsTeacher

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pi.ativas.MainActivity
import com.pi.ativas.R
import com.pi.ativas.base.BaseFragment
import com.pi.ativas.data.bodys.StudentsInClassBody
import com.pi.ativas.databinding.FragmentViewStudentsTeacherBinding
import com.pi.ativas.model.Classroom
import com.pi.ativas.model.User
import com.pi.ativas.teacher.createGroup.CreateGroupViewModel
import com.pi.ativas.teacher.createGroup.ItemClickListener
import com.pi.ativas.teacher.createGroup.StudentAdapter
import com.pi.ativas.teacher.model.DataForRequirement
import org.koin.androidx.viewmodel.ext.android.viewModel

class ViewStudentsTeacherFragment : BaseFragment() {
    private lateinit var binding: FragmentViewStudentsTeacherBinding
    private val viewModel: CreateGroupViewModel by viewModel()
    private lateinit var classroom: Classroom
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var dataForRequirement: DataForRequirement
    private val viewStudentsTeacherFragmentArgs: ViewStudentsTeacherFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        classroom = viewStudentsTeacherFragmentArgs.classroom
        dataForRequirement = viewStudentsTeacherFragmentArgs.dataForRequirement

        sharedPreferences = requireContext().getSharedPreferences("dataLogin", Context.MODE_PRIVATE)
      with(sharedPreferences) {
          getString("email", "")?.let { email ->
              getString("password", "")?.let { password ->
                  getString("token", "")?.let { token ->
                      dataForRequirement = DataForRequirement(email, password, token)
                  }
              }
          }
      }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).setTittleAppBar("Visualizar Aluno")
        binding = FragmentViewStudentsTeacherBinding.inflate(layoutInflater)
        initObservers()
        return binding.root
    }
    override fun initObservers() {
        val studentsInClassBody = StudentsInClassBody(
            email = dataForRequirement.email,
            token = dataForRequirement.token,
            password = dataForRequirement.password,
            class_id = classroom.id,
            type = 1
        )
        viewModel.getStudents(studentsInClassBody)
        viewModel.students.observe(viewLifecycleOwner) {
            recycleView(it)
        }
    }

    private fun recycleView(list: List<User>) {
        binding.progressbar.visibility = View.GONE

        val onClickListener = ItemClickListener { user ->

        }

        val recyclerView = binding.recycleView
        val adapter = StudentAdapter(list, onClickListener)
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }


}