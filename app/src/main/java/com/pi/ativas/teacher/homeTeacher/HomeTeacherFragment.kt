package com.pi.ativas.teacher.homeTeacher

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pi.ativas.MainActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pi.ativas.base.BaseFragment
import com.pi.ativas.data.bodys.RequestClassroomBody
import com.pi.ativas.data.bodys.RequestTaskBody
import com.pi.ativas.databinding.FragmentHomeTeacherBinding
import com.pi.ativas.model.Classroom
import com.pi.ativas.teacher.model.DataForRequirement
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeTeacherFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeTeacherBinding
    private lateinit var dataForRequirement: DataForRequirement
    private val homeTeacherViewModel: HomeTeacherViewModel by viewModel()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences("dataLogin", Context.MODE_PRIVATE)

        with(sharedPreferences) {
            getString("email", "")?.let { email ->
                getString("password", "")?.let { password ->
                    getString("token", "")?.let { token ->
                        dataForRequirement= DataForRequirement(email,password,token)
                        homeTeacherViewModel.getClassroom2(RequestClassroomBody(
                            email = email,
                            password = password,
                            token = token,
                        ))
                    }
                }
            }
        }
       /* with(sharedPreferences) {
            getString("email", "")?.let { email ->
                getString("password", "")?.let { password ->
                    getString("token", "")?.let { token ->
                        dataForRequirement = DataForRequirement(email, password, token)
                        homeTeacherViewModel.getClassroom(DataForRequirement(email,password,token))

                    }
                }
            }
        }*/

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).setTittleAppBar("Tela Inicial")

        binding = FragmentHomeTeacherBinding.inflate(layoutInflater)
        val activity: MainActivity = activity as MainActivity
        activity.getDrawerTeatcher()
        activity.setNavHeader("Walter White",dataForRequirement.email)
        initObservers()
        return binding.root
    }


    override fun initObservers() {

        homeTeacherViewModel.listClassroom.observe(viewLifecycleOwner) {
            recycleView(it)
        }

        homeTeacherViewModel.error.observe(viewLifecycleOwner){
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

    private fun recycleView(list: List<Classroom>) {
        binding.progressbar.visibility = View.GONE
        binding.bottomSheetBG.visibility = View.GONE


        val onClickListener = ItemClickListener { classroom ->
            val action =
                HomeTeacherFragmentDirections.actionHomeTeacherFragmentToTaskClassTeacherFragment(
                    classroom,
                    "1",
                    dataForRequirement
                )
            findNavController().navigate(action)
        }

        val recyclerView = binding.recycleViewHomeTeacher
        val adapter = ClassroomAdapter(list, onClickListener)
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

}