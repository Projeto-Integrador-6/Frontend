package com.pi.ativas.student.ui

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
import com.pi.ativas.base.BaseFragment
import com.pi.ativas.data.bodys.GetTeachersBody
import com.pi.ativas.data.bodys.RequestTaskBody
import com.pi.ativas.databinding.FragmentHomeStudentBinding
import com.pi.ativas.databinding.FragmentTeacherStudentBinding
import com.pi.ativas.model.Task
import com.pi.ativas.model.Teacher
import com.pi.ativas.student.adapter.ItemClickListenerTA
import com.pi.ativas.student.adapter.TaskAdapter
import com.pi.ativas.student.adapter.TeacherAdapter
import com.pi.ativas.student.viewmodel.HomeStudentViewModel
import com.pi.ativas.student.viewmodel.TeacherStudentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TeacherStudentFragment : BaseFragment() {

    private lateinit var binding: FragmentTeacherStudentBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val viewModel: TeacherStudentViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences("dataLogin", Context.MODE_PRIVATE)

        with(sharedPreferences) {
            getString("email", "")?.let { email ->
                getString("password", "")?.let { password ->
                    getString("token", "")?.let { token ->
                        viewModel.getTeachers(
                            GetTeachersBody(
                                email, password, token
                            )
                        )
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).setTittleAppBar("Professores")
        binding = FragmentTeacherStudentBinding.inflate(layoutInflater)
        initObservers()
        return binding.root
    }

    override fun initObservers() {
        viewModel.response.observe(viewLifecycleOwner) {
            recycleView(it)
            binding.progressbar.visibility = View.GONE
            binding.bottomSheetBG.visibility = View.GONE
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

    private fun recycleView(teachersList: List<Teacher>) {

        val onClickListener = ItemClickListenerTA { teacher ->

        }

        val recyclerView = binding.recycleView
        val adapter = TeacherAdapter(teachersList, onClickListener)
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

}