package com.pi.ativas.teacher.tasksClassTeacher

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.pi.ativas.data.bodys.RequestTaskBody
import com.pi.ativas.databinding.FragmentTaskClassTeacherBinding
import com.pi.ativas.teacher.model.Classroom
import com.pi.ativas.model.Task
import com.pi.ativas.teacher.model.DataForRequirement
import org.koin.androidx.viewmodel.ext.android.viewModel

class TasksClassTeacherFragment : Fragment() {

    private lateinit var binding: FragmentTaskClassTeacherBinding
    private lateinit var taskList: List<Task>
    private lateinit var classroom: Classroom
    private lateinit var dataForRequirement: DataForRequirement
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private val tasksClassTeacherViewModel: TasksClassTeacherViewModel by viewModel()
    private val classTeacherFragmentArgs: TasksClassTeacherFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        classroom = classTeacherFragmentArgs.classroom
        dataForRequirement = classTeacherFragmentArgs.dataForRequirement
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskClassTeacherBinding.inflate(layoutInflater)
        tasksClassTeacherViewModel.getClassroomTasks(
            RequestTaskBody(
                email = dataForRequirement.email,
                password = dataForRequirement.password,
                token = dataForRequirement.token,
                classId = classroom.id,
                taskType = null
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
            taskList = it
            recycleView()
        }

        tasksClassTeacherViewModel.taskButtonClick.observe(viewLifecycleOwner) {
            viewTask(it)
        }
    }

    private fun recycleView() {
        //binding.progressbar.visibility = View.GONE

        val onClickListener = ItemClickListener { task ->
/*            val action = ClassT
            findNavController().navigate(action)*/
        }

        val recyclerView = binding.recycleviewClassTeacher
        val adapter = TaskAdapter(taskList, onClickListener, tasksClassTeacherViewModel)
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