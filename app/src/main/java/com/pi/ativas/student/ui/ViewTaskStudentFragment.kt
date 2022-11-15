package com.pi.ativas.student.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.pi.ativas.R
import com.pi.ativas.base.BaseFragment
import com.pi.ativas.databinding.FragmentViewTaskStudentBinding

class ViewTaskStudentFragment: BaseFragment() {

    private lateinit var binding: FragmentViewTaskStudentBinding
    private val viewTasksStudentFragmentArgs: ViewTaskStudentFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewTaskStudentBinding.inflate(layoutInflater)
        initObservers()
        initViews()
        return binding.root
    }

    override fun initViews() {
        val task = viewTasksStudentFragmentArgs.task
        with(binding){
            txtTittle.text = task.question_title
            txtQuestion.text = task.question
            txtDeliveryDateLimit.text = task.limit_date
            txtDeliveryDate.text = task.delivery_date
            txtReductionFactor.text = task.reduction_factor.toString()
            txtQuestionResponse.text = getString(R.string.lorem_ipsum)
            imageResponse.visibility = View.VISIBLE
        }
    }
}