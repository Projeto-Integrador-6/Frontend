package com.pi.ativas.teacher.studentsTeacher

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pi.ativas.MainActivity
import com.pi.ativas.R
import com.pi.ativas.base.BaseFragment
import com.pi.ativas.data.bodys.RequestClassroomBody
import com.pi.ativas.databinding.FragmentStudentTeacherBinding
import com.pi.ativas.databinding.FragmentTaskHistoryTeacherBinding
import com.pi.ativas.model.Classroom
import com.pi.ativas.teacher.homeTeacher.ClassroomAdapter
import com.pi.ativas.teacher.homeTeacher.HomeTeacherViewModel
import com.pi.ativas.teacher.homeTeacher.ItemClickListener
import com.pi.ativas.teacher.model.DataForRequirement
import com.pi.ativas.teacher.taskHistoryTeacher.TaskHistoryTeacherFragmentDirections
import org.koin.androidx.viewmodel.ext.android.viewModel

class StudentTeacherFragment : BaseFragment() {
    private lateinit var binding: FragmentStudentTeacherBinding
    private val viewModel: HomeTeacherViewModel by viewModel()
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var dataForRequirement: DataForRequirement

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        viewModel.getClassroom2(
            RequestClassroomBody(email = dataForRequirement.email,
            password = dataForRequirement.password,
            token = dataForRequirement.token)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).setTittleAppBar("Visualizar Aluno")
        binding = FragmentStudentTeacherBinding.inflate(layoutInflater)
        initObservers()
        return binding.root
    }

    override fun initObservers() {
        viewModel.listClassroom.observe(viewLifecycleOwner) {
            recycleView(it)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            binding.progressbar.visibility = View.GONE
            binding.bottomSheetBG.visibility = View.GONE
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Erro $it")
                .setMessage("Ocorreu um erro inesperado!")
                .setPositiveButton("Ok") { dialog, which ->
                }
                .show()
        }
    }
    private fun recycleView(list: List<Classroom>) {
        binding.progressbar.visibility = View.GONE
        binding.bottomSheetBG.visibility = View.GONE


        val onClickListener = ItemClickListener { teste ->
            val action =
               StudentTeacherFragmentDirections.actionStudentsTeacherFragmentToViewStudentsTeacherFragment(teste,dataForRequirement)
            findNavController().navigate(action)
        }

        val recyclerView = binding.recycleViewHistory
        val adapter = ClassroomAdapter(list, onClickListener)
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

}