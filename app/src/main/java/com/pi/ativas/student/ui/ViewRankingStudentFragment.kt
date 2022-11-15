package com.pi.ativas.student.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.pi.ativas.MainActivity
import com.pi.ativas.base.BaseFragment
import com.pi.ativas.databinding.FragmentViewRankingBinding

class ViewRankingStudentFragment: BaseFragment() {

    private lateinit var binding: FragmentViewRankingBinding
    private val viewRankingStudentFragmentArgs: ViewRankingStudentFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).setTittleAppBar("Ranking")
        binding = FragmentViewRankingBinding.inflate(layoutInflater)
        initViews()
        return binding.root
    }

    override fun initViews() {
        val dataViewRanking = viewRankingStudentFragmentArgs.dataViewRanking
        super.initViews()
        with(binding){
            txtPositionNumber.text = dataViewRanking.studentPosition
            txtSeusDadosNome.setText(dataViewRanking.studentName)
            txtPontuacao.setText(dataViewRanking.pontuation)
            txtPorcentagem.setText(dataViewRanking.porcentage)
            txtNumeroTasks.setText(dataViewRanking.numberTasks)
            txtNumeroAlunos.setText(dataViewRanking.numberStudents)
        }
    }
}