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
import com.pi.ativas.data.bodys.LoginBody
import com.pi.ativas.data.bodys.RequestTaskBody
import com.pi.ativas.databinding.FragmentDisciplineStudentBinding
import com.pi.ativas.model.Discipline
import com.pi.ativas.model.Task
import com.pi.ativas.student.adapter.DisciplineAdapter
import com.pi.ativas.student.adapter.ItemClickListenerDA
import com.pi.ativas.student.adapter.TaskAdapter
import com.pi.ativas.student.viewmodel.DisciplineStudentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DisciplineStudentFragment : BaseFragment() {
    private lateinit var binding: FragmentDisciplineStudentBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val viewModel: DisciplineStudentViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences("dataLogin", Context.MODE_PRIVATE)

        with(sharedPreferences) {
            getString("email", "")?.let { email ->
                getString("password", "")?.let { password ->
                    getString("token", "")?.let { token ->
                        viewModel.getDisciplines(LoginBody(email, password, token))
                    }
                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).setTittleAppBar("Disciplinas")
        binding = FragmentDisciplineStudentBinding.inflate(layoutInflater)
        initObservers()
        return binding.root
    }

    override fun initObservers() {
        super.initObservers()
        viewModel.listDisciplines.observe(viewLifecycleOwner) {
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

    private fun recycleView(disciplineList: List<Discipline>) {

        val onClickListener = ItemClickListenerDA {

        }

        val recyclerView = binding.recycleView
        val adapter = DisciplineAdapter(disciplineList, onClickListener)
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }
}