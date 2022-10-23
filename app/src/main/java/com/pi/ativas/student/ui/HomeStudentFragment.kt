package com.pi.ativas.student.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.pi.ativas.base.BaseFragment
import com.pi.ativas.data.bodys.RequestTaskBody
import com.pi.ativas.databinding.FragmentHomeStudentBinding
import com.pi.ativas.model.Task
import com.pi.ativas.student.adapter.ItemClickListener
import com.pi.ativas.student.adapter.TaskAdapter
import com.pi.ativas.student.viewmodel.HomeStudentViewModel
import com.pi.ativas.teacher.model.DataForRequirement
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeStudentFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeStudentBinding
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
                                taskType = null
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
        initObservers()
        binding = FragmentHomeStudentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initObservers() {
        viewModel.listTask.observe(viewLifecycleOwner){
            recycleView(it)
            binding.progressbar.visibility = View.GONE
        }
    }

    private fun recycleView(taskList: List<Task>) {
        binding.progressbar.visibility = View.GONE

        val onClickListener = ItemClickListener { task ->
            Toast.makeText(requireContext(), task.toString(), Toast.LENGTH_SHORT).show()
        }

        val recyclerView = binding.recycleViewHomeStudent
        val adapter = TaskAdapter(taskList, onClickListener)
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

}