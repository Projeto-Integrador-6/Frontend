package com.pi.ativas.teacher.taskTeams

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
import com.pi.ativas.base.BaseFragment
import com.pi.ativas.data.bodys.RequestTaskTeamsBody
import com.pi.ativas.databinding.FragmentTaskTeamsBinding
import com.pi.ativas.teacher.model.Team
import com.pi.ativas.teacher.tasksClassTeacher.TaskAdapter
import com.pi.ativas.teacher.tasksClassTeacher.TasksClassTeacherFragmentDirections
import org.koin.androidx.viewmodel.ext.android.viewModel

class TaskTeamsFragment : BaseFragment() {

    private lateinit var binding: FragmentTaskTeamsBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val taskTeamsFragmentArgs: TaskTeamsFragmentArgs by navArgs()
    private val taskTeamsViewModel: TaskTeamsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences("dataLogin", Context.MODE_PRIVATE)
        Log.i("TESTE", "onCreate: "+taskTeamsFragmentArgs.idTask)
        with(sharedPreferences) {
            getString("email", "")?.let { email ->
                getString("password", "")?.let { password ->
                    getString("token", "")?.let { token ->
                        taskTeamsViewModel.getTaskTeams(
                            RequestTaskTeamsBody(
                                email = email,
                                password = password,
                                token = token,
                                taskId = taskTeamsFragmentArgs.idTask
                            )
                        )
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        initObservers()
        binding = FragmentTaskTeamsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initObservers() {
        taskTeamsViewModel.listTeams.observe(viewLifecycleOwner){
            binding.progressBar.visibility = View.GONE
            binding.bottomSheetBG.visibility = View.GONE
            recycleView(it)
        }

        taskTeamsViewModel.error.observe(viewLifecycleOwner){
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

    private fun recycleView(teamList: List<Team>) {

        val onClickListener = ItemClickListener { team ->
        }

        val recyclerView = binding.recycleviewTeam
        val adapter = TeamAdapter(teamList, onClickListener)
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }
}