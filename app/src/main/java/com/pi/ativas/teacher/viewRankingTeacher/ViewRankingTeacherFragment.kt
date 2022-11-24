package com.pi.ativas.teacher.viewRankingTeacher

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pi.ativas.MainActivity
import com.pi.ativas.base.BaseFragment
import com.pi.ativas.data.bodys.RequestRankingBody
import com.pi.ativas.databinding.FragmentRankStudentBinding
import com.pi.ativas.model.RankingStudent
import com.pi.ativas.student.adapter.ItemClickListenerRA
import com.pi.ativas.student.adapter.RankingAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class ViewRankingTeacherFragment: BaseFragment() {

    private lateinit var binding: FragmentRankStudentBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val viewModel: ViewRankingTeacherViewModel by viewModel()
    private val viewRankingTeacherFragmentArgs : ViewRankingTeacherFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences("dataLogin", Context.MODE_PRIVATE)

        with(sharedPreferences) {
            getString("email", "")?.let { email ->
                getString("password", "")?.let { password ->
                    getString("token", "")?.let { token ->
                        viewModel.getRanking(
                            RequestRankingBody(
                                email = email,
                                password = password,
                                token = token,
                                classId = viewRankingTeacherFragmentArgs.classroomId
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
        (activity as MainActivity).setTittleAppBar("Ranking")
        binding = FragmentRankStudentBinding.inflate(layoutInflater)
        initObservers()
        return binding.root
    }

    override fun initObservers() {
        super.initObservers()

        viewModel.response.observe(viewLifecycleOwner) {
            it.content?.let { list ->
                if (list.isNotEmpty()) recycleView(list) else noTasks()
                binding.progressbar.visibility = View.GONE
                binding.bottomSheetBG.visibility = View.GONE
            }

            if (!it.success){
                Toast.makeText(requireContext(), "Ocorreu um erro inesperado! Tente novamente!", Toast.LENGTH_SHORT).show()
            }
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
        binding.txtNoRank.visibility = View.VISIBLE
    }
    private fun recycleView(rankingStudentList: List<RankingStudent>) {

        val onClickListener = ItemClickListenerRA { rankingStudent ->

        }

        val recyclerView = binding.recycleView
        val adapter = RankingAdapter(rankingStudentList, onClickListener)
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }
}