package com.pi.ativas.teacher.upsetReport

import android.graphics.BitmapFactory
import android.opengl.Visibility
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pi.ativas.MainActivity
import com.pi.ativas.R
import com.pi.ativas.data.bodys.RequestTaskTeamsBody
import com.pi.ativas.data.bodys.StudentsInTeamBody
import com.pi.ativas.data.bodys.UpsetReportsBody
import com.pi.ativas.databinding.FragmentUpsetReportBinding
import com.pi.ativas.model.Report
import com.pi.ativas.model.Task
import com.pi.ativas.teacher.model.DataForRequirement
import com.pi.ativas.teacher.model.Team
import com.pi.ativas.teacher.taskTeams.TaskTeamsViewModel
import org.joda.time.DateTime
import org.joda.time.Days
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.math.log


class UpsetReportFragment : Fragment() {
    private lateinit var binding: FragmentUpsetReportBinding
    private val upsetReportViewModel: UpsetReportViewModel by viewModel()
    private lateinit var report: Report
    private lateinit var task: Task
    private lateinit var team: Team
    private lateinit var dataForRequirement: DataForRequirement
    private val upsetReportTeacherFragment: UpsetReportFragmentArgs by navArgs()
    private lateinit var teamList: List<Team>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        report = upsetReportTeacherFragment.report
        dataForRequirement = upsetReportTeacherFragment.dataForRequirement
        task = upsetReportTeacherFragment.task
        Log.i("TESTE", "onCreate: " + task)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).setTittleAppBar("Corrigir Atividade")

        binding = FragmentUpsetReportBinding.inflate(layoutInflater)
        upsetReportViewModel.getTeams(
            RequestTaskTeamsBody(
                email = dataForRequirement.email,
                password = dataForRequirement.password,
                token = dataForRequirement.token,
                taskId = task.id.toString()
            )
        )

        initObservers()
        initViews()
        return binding.root
    }

    private fun initViews() {
        with(binding) {
            progressBarLogin.visibility = View.VISIBLE
            upsetReportViewModel.listTeams.observe(viewLifecycleOwner) {
                it.forEach {
                    if (it.id.toInt() == report.team_id) {
                        upsetReportViewModel.getStudentsInTeam(
                            StudentsInTeamBody(
                                email = dataForRequirement.email,
                                password = dataForRequirement.password,
                                token = dataForRequirement.token,
                                teamId = it.id
                            )
                        )
                        upsetReportViewModel.listStudents.observe(viewLifecycleOwner) {
                            txtGroupMembers.setText(it.toString().substring(1, it.toString().length - 1))
                            progressBarLogin.visibility = View.GONE

                        }
                        txtDate.setText(task.limit_date)
                        titleTask.text = task.question_title
                        questionTask.text = task.question
                        txtGroupName.setText(it.name)
                        txtReportDate.setText(report.data)
                        responseEditText.setText(report.answer)
                        try {
                            report.anexo?.let { photo ->
                                val imagemBites: ByteArray
                                imagemBites = Base64.decode(photo, Base64.DEFAULT)
                                val imagemdecodificada =
                                    BitmapFactory.decodeByteArray(imagemBites, 0, imagemBites.size)
                                imgFoto.setImageBitmap(imagemdecodificada)
                            } ?: imgFoto.setImageResource(R.drawable.image_example)
                        } catch (e: java.lang.Exception) {
                            imgFoto.setImageResource(R.drawable.image_example)
                        }
                        val limitdate = DateTime(
                            task.limit_date.substring(0, 4).toInt(),
                            task.limit_date.substring(5, 7).toInt(),
                            task.limit_date.substring(8, 10).toInt(),
                            task.limit_date.substring(11, 13).toInt(),
                            task.limit_date.substring(14, 16).toInt()
                        )
                        val dateReport =
                            report.data?.substring(0, 4)?.let { it1 ->
                                DateTime(
                                    it1.toInt(),
                                    report.data!!.substring(5, 7).toInt(),
                                    report.data!!.substring(8, 10).toInt(),
                                    report.data!!.substring(11, 13).toInt(),
                                    report.data!!.substring(14, 16).toInt()
                                )
                            }
                        val dias: Int = Days.daysBetween(limitdate, dateReport).getDays()
                        txtReductor.setText(task.reduction_factor.toString()+" pontos reduzidos por dia")
                        if (dias > 0) {
                            txtDaysLate.setText("Tarefa entregue com "+dias.toString()+" dia(s) de atraso")
                            if((task.pontuation-task.reduction_factor*dias)<=0){
                                txtPontuationAvailable.setText("Pontuação maxima que o aluno pode obter:"+(task.pontuation-task.pontuation))
                            }else {
                                txtPontuationAvailable.setText("Pontuação maxima que o aluno pode obter:" + (task.pontuation - task.reduction_factor * dias).toString())
                            }
                        }else{
                            txtDaysLate.setText("Tarefa entregue com "+dias * -1+" dia(s) de antecedência")
                            txtPontuationAvailable.setText("Pontuação maxima que o aluno pode obter:"+task.pontuation.toString())
                        }

                        txtStartPontuation.setText(task.pontuation.toString())
                    }

                }
                btnCorrigir.setOnClickListener{
                    if(txtPontuationReport.text.toString().toInt()<=txtPontuationAvailable.text.toString().split(":").get(1).toInt()) {
                        upsetReportViewModel.upsetReport(
                            UpsetReportsBody(
                                token = dataForRequirement.token,
                                email = dataForRequirement.email,
                                password = dataForRequirement.password,
                                pontuation = txtPontuationReport.text.toString().toInt(),
                                reportId = report.id.toString().toInt()
                            )
                        )
                        MaterialAlertDialogBuilder(requireContext())
                            .setTitle("Atividade corrigida!")
                            .setMessage("Você corrigiu essa atividade!")
                            .setPositiveButton("Ok") { dialog, which ->
                                findNavController().popBackStack(R.id.taskReportsTeacherFragment,false)
                            }
                            .show()
                    }else{
                        Toast.makeText(
                            requireContext(),
                            "O valor maximo de pontuação permitido é "+txtPontuationAvailable.text.toString().split(":").get(1).toInt(),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            }
        }

    }

    fun initObservers() {


    }

}