package com.pi.ativas.teacher.tasksClassTeacher

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
import com.pi.ativas.MainActivity
import com.pi.ativas.data.bodys.RequestTaskBody
import com.pi.ativas.databinding.FragmentTaskClassTeacherBinding
import com.pi.ativas.model.Classroom
import com.pi.ativas.model.Task
import com.pi.ativas.teacher.model.DataForRequirement
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.log

class TasksClassTeacherFragment : Fragment() {

    private lateinit var binding: FragmentTaskClassTeacherBinding
    private lateinit var taskList: List<Task>
    private lateinit var classroom: Classroom
    private lateinit var dataForRequirement: DataForRequirement
    private lateinit var type: String
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private val tasksClassTeacherViewModel: TasksClassTeacherViewModel by viewModel()
    private val tasksClassTeacherFragmentArgs: TasksClassTeacherFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        classroom = tasksClassTeacherFragmentArgs.classroom
        dataForRequirement = tasksClassTeacherFragmentArgs.dataForRequirement
        type = tasksClassTeacherFragmentArgs.type
        Log.i("TESTE", "onCreate: TYPE:"+type)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).setTittleAppBar("Atividades da Turma")

        binding = FragmentTaskClassTeacherBinding.inflate(layoutInflater)
        tasksClassTeacherViewModel.getClassroomTasks(
            RequestTaskBody(
                email = dataForRequirement.email,
                password = dataForRequirement.password,
                token = dataForRequirement.token,
                classId = classroom.id,
                taskType = type.toInt()
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
        tasksClassTeacherViewModel.listTask.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = View.GONE
            binding.bottomSheetBG.visibility = View.GONE
            recycleView(it)
        }

        tasksClassTeacherViewModel.taskButtonClick.observe(viewLifecycleOwner) {
            viewTask(it)
        }

        tasksClassTeacherViewModel.error.observe(viewLifecycleOwner){
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

    private fun recycleView(list: List<Task>) {

        val onClickListener = ItemClickListener { task ->
            val action = TasksClassTeacherFragmentDirections.actionTaskClassTeacherFragmentToTaskReportTeacherFragment(task,task.id.toString(),dataForRequirement)
            findNavController().navigate(action)
        }

        val recyclerView = binding.recycleviewClassTeacher
        val adapter = TaskAdapter(list, onClickListener, tasksClassTeacherViewModel)
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    private fun viewTask(task: Task) {
        binding.bottomSheet.apply {
            titleTask.text = "Titulo da atividade"
            questionTask.text = task.question
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