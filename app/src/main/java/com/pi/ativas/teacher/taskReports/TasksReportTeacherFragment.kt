package com.pi.ativas.teacher.taskReports

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pi.ativas.data.bodys.GetReportsBody
import com.pi.ativas.data.bodys.RequestTaskBody
import com.pi.ativas.data.bodys.RequestTaskTeamsBody
import com.pi.ativas.databinding.FragmentTaskClassTeacherBinding
import com.pi.ativas.model.Classroom
import com.pi.ativas.model.Report
import com.pi.ativas.model.Task
import com.pi.ativas.teacher.model.DataForRequirement
import org.koin.androidx.viewmodel.ext.android.viewModel

class TasksReportTeacherFragment : Fragment() {

    private lateinit var binding: FragmentTaskClassTeacherBinding
    private lateinit var taskList: List<Report>
    private lateinit var task: Task
    private lateinit var dataForRequirement: DataForRequirement
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private val tasksReportTeacherViewModel: TasksReportTeacherViewModel by viewModel()
    private val tasksReportTeacherFragmentArgs: TasksReportTeacherFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        task = tasksReportTeacherFragmentArgs.task
        dataForRequirement = tasksReportTeacherFragmentArgs.dataForRequirement
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskClassTeacherBinding.inflate(layoutInflater)
        tasksReportTeacherViewModel.getTaskReports(
            GetReportsBody(
                email = dataForRequirement.email,
                password = dataForRequirement.password,
                token = dataForRequirement.token,
                task_id = task.id,
                type = 1
            )
        )
        tasksReportTeacherViewModel.getTaskTeams(
            RequestTaskTeamsBody(
                email = dataForRequirement.email,
                password = dataForRequirement.password,
                token = dataForRequirement.token,
                taskId = task.id.toString()
            )
        )
        initObservers()
        initViews()
        return binding.root
    }

    private fun initViews() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet.bottomSheet)

        bottomSheetBehavior.apply {
            peekHeight = 0
            state = BottomSheetBehavior.STATE_COLLAPSED
        }
        binding.bottomSheetBG.setOnClickListener{
            dismiss()
        }
    }

    private fun initObservers() {
        tasksReportTeacherViewModel.listReport.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = View.GONE
            binding.bottomSheetBG.visibility = View.GONE
            taskList = it
            recycleView()
        }

        tasksReportTeacherViewModel.taskButtonClick.observe(viewLifecycleOwner) {
            viewTask(it)
        }

        tasksReportTeacherViewModel.error.observe(viewLifecycleOwner){
            binding.progressBar.visibility = View.GONE
            binding.bottomSheetBG.visibility = View.GONE
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Erro $it")
                .setMessage("Ocorreu um erro inesperado!")
                .setPositiveButton("Ok") { dialog, which ->
                }
                .show()
        }
    }

    private fun recycleView() {

        val onClickListener = ItemClickListener { report ->
           val action = TasksReportTeacherFragmentDirections.actionTaskReportTeacherFragmentToUpsetTaskTeacherFragment(report,dataForRequirement,task)
            Log.i("TESTE", "recycleView: REPORT E DATA: "+report+" - "+dataForRequirement)
           findNavController().navigate(action)
        }

        val recyclerView = binding.recycleviewClassTeacher
        val adapter = ReportAdapter(taskList, onClickListener, tasksReportTeacherViewModel)
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    private fun viewTask(report: Report) {
        binding.bottomSheet.apply {
            titleTask.text = "Repostas da atividade"
            questionTask.text = report.answer
        }

        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        binding.bottomSheetBG.isVisible = true

    }

    private fun dismiss() {
        binding.bottomSheetBG.visibility = View.GONE
        Handler(Looper.getMainLooper()).postDelayed({
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }, 100)
    }

}