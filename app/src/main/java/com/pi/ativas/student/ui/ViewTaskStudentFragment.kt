package com.pi.ativas.student.ui

import android.content.Context
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.pi.ativas.MainActivity
import com.pi.ativas.R
import com.pi.ativas.base.BaseFragment
import com.pi.ativas.data.bodys.GetReportsBody
import com.pi.ativas.data.bodys.GetStudentReportBody
import com.pi.ativas.databinding.FragmentViewTaskStudentBinding
import com.pi.ativas.student.viewmodel.TasksReportsStudentViewModel
import com.pi.ativas.teacher.taskReports.TasksReportTeacherViewModel
import org.joda.time.DateTime
import org.joda.time.Days
import org.koin.androidx.viewmodel.ext.android.viewModel

class ViewTaskStudentFragment: BaseFragment() {

    private lateinit var binding: FragmentViewTaskStudentBinding
    private val viewTasksStudentFragmentArgs: ViewTaskStudentFragmentArgs by navArgs()
    private val viewModel: TasksReportsStudentViewModel by viewModel()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sharedPreferences = requireContext().getSharedPreferences("dataLogin", Context.MODE_PRIVATE)
        (activity as MainActivity).setTittleAppBar("Atividades")
        binding = FragmentViewTaskStudentBinding.inflate(layoutInflater)
        viewModel.getTaskReports(
            GetStudentReportBody(
                email = sharedPreferences.getString("email", "")!!,
                password = sharedPreferences.getString("password", "")!!,
                token = sharedPreferences.getString("token", "")!!,
                type = 0
            )
        )
        initObservers()
        initViews()
        return binding.root
    }

    override fun initViews() {
    }
    override fun initObservers() {
        val task = viewTasksStudentFragmentArgs.task
        with(binding) {
            viewModel.listReport.observe(viewLifecycleOwner) {
                it.forEach {
                    if(it.task_id == task.id){
                        txtTittle.text = task.question_title
                        txtQuestion.text = task.question
                        txtDeliveryDateLimit.text = task.limit_date
                        txtDeliveryDate.text = it.data
                        txtReductionFactor.text = task.reduction_factor.toString()
                        txtQuestionResponse.text = it.answer
                        val limitdate = DateTime(
                            task.limit_date.substring(0, 4).toInt(),
                            task.limit_date.substring(5, 7).toInt(),
                            task.limit_date.substring(8, 10).toInt(),
                            task.limit_date.substring(11, 13).toInt(),
                            task.limit_date.substring(14, 16).toInt()
                        )
                        val dateReport =
                            it.data?.substring(0, 4)?.let { it1 ->
                                DateTime(
                                    it1.toInt(),
                                    it.data!!.substring(5, 7).toInt(),
                                    it.data!!.substring(8, 10).toInt(),
                                    it.data!!.substring(11, 13).toInt(),
                                    it.data!!.substring(14, 16).toInt()
                                )
                            }
                        val dias: Int = Days.daysBetween(limitdate, dateReport).getDays()
                        if (dias > 0) {
                            if((task.pontuation-task.reduction_factor*dias)<=0){
                                txtPontuationAvailable.setText((task.pontuation-task.pontuation))
                            }else {
                                txtPontuationAvailable.setText((task.pontuation - task.reduction_factor * dias).toString())
                            }
                        }else{
                            txtPontuationAvailable.setText(task.pontuation.toString())
                        }
                        txtPontuation.text=task.pontuation.toString()
                        try {
                            it.anexo?.let { photo ->
                                val imagemBites: ByteArray
                                imagemBites = Base64.decode(photo, Base64.DEFAULT)
                                val imagemdecodificada =
                                    BitmapFactory.decodeByteArray(imagemBites, 0, imagemBites.size)
                                imageResponse.setImageBitmap(imagemdecodificada)
                                imageResponse.visibility = View.VISIBLE
                            } ?: imageResponse.setImageResource(R.drawable.image_example)
                        } catch (e: java.lang.Exception) {
                            imageResponse.setImageResource(R.drawable.image_example)
                        }
                        Log.i("TESTE", "initObservers: "+it.Pontuation.toString())
                        if(it.Pontuation == null) {
                            txtYourPontuation.text = "Atividade ainda não foi corrigida"
                        }else{
                            txtYourPontuation.text = it.Pontuation.toString()
                        }
                    }
                    progressbar.visibility = View.GONE
                }
            }
        }

    }
}