package com.pi.ativas.teacher.classTeacher

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.pi.ativas.data.bodys.RequestTaskBody
import com.pi.ativas.databinding.FragmentClassTeacherBinding
import com.pi.ativas.teacher.model.Classroom
import com.pi.ativas.model.Task
import com.pi.ativas.teacher.model.DataForRequirement
import org.koin.androidx.viewmodel.ext.android.viewModel

class ClassTeacherFragment : Fragment() {

    private lateinit var binding: FragmentClassTeacherBinding
    private lateinit var taskList: List<Task>
    private lateinit var classroom: Classroom
    private lateinit var dataForRequirement: DataForRequirement
    private val classTeacherViewModel: ClassTeacherViewModel by viewModel()
    private val classTeacherFragmentArgs: ClassTeacherFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        classroom = classTeacherFragmentArgs.classroom
        dataForRequirement = classTeacherFragmentArgs.dataForRequirement
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentClassTeacherBinding.inflate(layoutInflater)
        classTeacherViewModel.getClassroomTasks(RequestTaskBody(
            email = dataForRequirement.email,
            password = dataForRequirement.password,
            token = dataForRequirement.token,
            classId = classroom.id,
            taskType = null
        ))
        initObservers()
        return binding.root
    }

    private fun initObservers(){
        classTeacherViewModel.listTask.observe(viewLifecycleOwner){
            binding.progressBar.visibility = View.GONE
            binding.bottomSheetBG.visibility = View.GONE
            taskList = it
            recycleView()
        }
    }

    private fun recycleView() {
        //binding.progressbar.visibility = View.GONE

        val onClickListener = ItemClickListener { task ->
/*            val action = ClassT
            findNavController().navigate(action)*/
        }

        val recyclerView = binding.recycleviewClassTeacher
        val adapter = TaskAdapter(taskList, onClickListener)
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

}