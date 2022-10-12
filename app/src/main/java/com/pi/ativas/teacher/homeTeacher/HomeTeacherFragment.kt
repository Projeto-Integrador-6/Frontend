package com.pi.ativas.teacher.homeTeacher

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.pi.ativas.databinding.FragmentHomeTeacherBinding
import com.pi.ativas.model.Classroom
import com.pi.ativas.model.User
import com.pi.ativas.teacher.model.DataForRequirement
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeTeacherFragment : Fragment() {

    private lateinit var binding: FragmentHomeTeacherBinding
    private lateinit var teacher: User
    private lateinit var dataForRequirement: DataForRequirement
    private lateinit var classroomList: List<Classroom>
    private val homeTeacherViewModel: HomeTeacherViewModel by viewModel()
    private val homeTeacherFragmentArgs: HomeTeacherFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        teacher = homeTeacherFragmentArgs.userTeacher
        dataForRequirement = homeTeacherFragmentArgs.dataForRequirement
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeTeacherBinding.inflate(layoutInflater)
        initObservers()
        homeTeacherViewModel.getClassroom(dataForRequirement)

        return binding.root
    }


    private fun initObservers(){
        homeTeacherViewModel.listClassroom.observe(viewLifecycleOwner){
            classroomList = it
            recycleView()
        }
    }

    private fun recycleView() {
        binding.progressbar.visibility = View.GONE

        val onClickListener = ItemClickListener { classroom ->
            val action = HomeTeacherFragmentDirections.actionHomeTeacherFragmentToClassTeacherFragment(classroom, dataForRequirement)
            findNavController().navigate(action)
        }

        val recyclerView = binding.recycleViewHomeTeacher
        val adapter = ClassroomAdapter(classroomList, onClickListener)
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

}