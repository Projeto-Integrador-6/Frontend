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
import com.pi.ativas.base.BaseFragment
import com.pi.ativas.data.bodys.RequestTaskBody
import com.pi.ativas.databinding.FragmentTaskHistoryStudentBinding
import com.pi.ativas.model.Task
import com.pi.ativas.student.adapter.ItemClickListener
import com.pi.ativas.student.adapter.TaskAdapter
import com.pi.ativas.student.viewmodel.HomeStudentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TaskHistoryStudentFragment : BaseFragment() {

    private lateinit var binding: FragmentTaskHistoryStudentBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val viewModel: HomeStudentViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences("dataLogin", Context.MODE_PRIVATE)

        with(sharedPreferences) {
            getString("email", "")?.let { email ->
                getString("password", "")?.let { password ->
                    getString("token", "")?.let { token ->
                        viewModel.getTask(
                            RequestTaskBody(
                                email = email,
                                password = password,
                                token = token,
                                classId = 0,
                                //0 -> Todas as tarefas
                                taskType = 0
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
        binding=FragmentTaskHistoryStudentBinding.inflate(layoutInflater)
        initObservers()
        return binding.root
    }

    override fun initObservers() {
        viewModel.listTask.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) recycleView(it) else noTasks()
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

    private fun noTasks() {
        binding.txtNoTasks.visibility = View.VISIBLE
    }

    private fun recycleView(taskList: List<Task>) {

        val onClickListener = ItemClickListener { task ->
            val action =
                TaskHistoryStudentFragmentDirections.actionTaskHistoryStudentFragmentToViewTaskStudentFragment(task)
            findNavController().navigate(action)
        }

        val recyclerView = binding.recycleView
        val adapter = TaskAdapter(taskList, onClickListener)
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }
}