package com.pi.ativas.student.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pi.ativas.MainActivity
import com.pi.ativas.base.BaseFragment
import com.pi.ativas.data.bodys.RequestRankingBody
import com.pi.ativas.data.responses.RequestRankingResponse
import com.pi.ativas.databinding.FragmentRankStudentBinding
import com.pi.ativas.model.RankingStudent
import com.pi.ativas.student.adapter.ItemClickListenerRA
import com.pi.ativas.student.adapter.RankingAdapter
import com.pi.ativas.student.model.DataViewRanking
import com.pi.ativas.student.viewmodel.RankStudentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RankStudentFragment : BaseFragment() {

    private lateinit var binding: FragmentRankStudentBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val viewModel: RankStudentViewModel by viewModel()

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
                                token = token
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
        (activity as MainActivity).setTittleAppBar("Ranking")
        binding = FragmentRankStudentBinding.inflate(layoutInflater)
        initObservers()
        return binding.root

    }

    override fun initObservers() {
        super.initObservers()

        viewModel.response.observe(viewLifecycleOwner) {
            verifyResponse(it)
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

    private fun verifyResponse(requestRankingResponse: RequestRankingResponse) {
        requestRankingResponse.content?.let { list ->
            if (list.size > 1) {
                recycleView(requestRankingResponse.content)
            } else {
                val content = requestRankingResponse.content[0]
                val action =
                    RankStudentFragmentDirections.actionRankStudentFragmentToViewRankingStudentFragment(
                        DataViewRanking(
                            studentName = content.student_name,
                            pontuation = content.pontuation.toString(),
                            porcentage = content.porcentage.toString(),
                            numberTasks = requestRankingResponse.numberOfTasks.toString(),
                            numberStudents = requestRankingResponse.numberOfStudents.toString(),
                            studentPosition = requestRankingResponse.studentPosition.toString()
                        )
                    )
                findNavController().navigate(action)
            }
        }
    }
}