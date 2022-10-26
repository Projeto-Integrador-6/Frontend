package com.pi.ativas.teacher.newTaskTeacher

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pi.ativas.R
import com.pi.ativas.base.BaseFragment
import com.pi.ativas.data.bodys.InsertTaskBody
import com.pi.ativas.databinding.FragmentNewTaskTeacherBinding
import com.pi.ativas.model.Classroom
import com.pi.ativas.teacher.homeTeacher.HomeTeacherViewModel
import com.pi.ativas.teacher.model.DataForRequirement
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class NewTaskTeacherFragment : BaseFragment() {
    private lateinit var binding: FragmentNewTaskTeacherBinding
    var cal = Calendar.getInstance()
    private val newTaskViewModel: NewTaskViewModel by viewModel()
    private lateinit var dataForRequirement: DataForRequirement
    private lateinit var sharedPreferences: SharedPreferences
    private var classroom=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences("dataLogin", Context.MODE_PRIVATE)

        with(sharedPreferences) {
            getString("email", "")?.let { email ->
                getString("password", "")?.let { password ->
                    getString("token", "")?.let { token ->
                        dataForRequirement = DataForRequirement(email, password, token)
                    }
                }
            }}

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewTaskTeacherBinding.inflate(layoutInflater)
        initObservers()
        newTaskViewModel.getClassroom(dataForRequirement)
        initViews()

        return binding.root
    }
    override fun initObservers() {
        newTaskViewModel.listClassroom.observe(viewLifecycleOwner) {
            val adpter: ArrayAdapter<Classroom> = ArrayAdapter<Classroom>(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                it
            )
            adpter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spnClass.adapter = adpter
            binding.spnClass.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    classroom = binding.spnClass.selectedItemPosition
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
            })
            newTaskViewModel.success.observe(viewLifecycleOwner) {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("NOVA ATIVIDADE CRIADA COM SUCESSO")
                    .setMessage(newTaskViewModel.success.toString())
                    .setPositiveButton("OK") { dialog, which ->
                    }
                    .show()
            }
        }


    }
    override fun initViews() {
        with(binding) {

            val txtTaskLimitDate2: TextView = txtTaskLimitDate
            var (radioGrupo: Int, radioRank: Int, radioCorrigir: Int) = setRadios()
            setDate(txtTaskLimitDate2)
            spnClass.adapter
            btnCriarAtividade.setOnClickListener{
                var memberInt =1
                if(!txtMemberLimit.text.toString().equals("")){
                    memberInt = txtMemberLimit.text.toString().toInt()
                }
                val insertTaskBody = InsertTaskBody(
                    email = dataForRequirement.email,
                    password = dataForRequirement.password,
                    token = dataForRequirement.token,
                    class_id = classroom+1,
                    grouped = radioGrupo,
                    rank = radioRank,
                    limit_date = txtTaskLimitDate2.text.toString(),
                    pontuation = txtTaskPontuation?.text.toString().toInt(),
                    answer = txtTaskAnswer?.text.toString(),
                    question_title = txtTaskTitle?.text.toString(),
                    question = txtTaskDescription.text.toString(),
                    correction = radioCorrigir,
                    file = "dsadas",
                    member_limiti = memberInt,
                    reduction_factor = txtTaksReductionFactor.text.toString().toFloat()
                )
                Log.i("TESTE", "initViews: "+insertTaskBody)
                newTaskViewModel.newTask(insertTaskBody)
            }
        }
    }

    private fun FragmentNewTaskTeacherBinding.setRadios(): Triple<Int, Int, Int> {
        radioGrupo.setOnClickListener {
            txtMemberLimit.visibility = View.VISIBLE
        }
        radioIndividual.setOnClickListener {
            txtMemberLimit.visibility = View.GONE
        }
        var radioGrupo : Int = 0
        var radioRank : Int = 0
        var radioCorrigir: Int = 0
        if (radioSimRank.isChecked) {
            radioRank = 1
        } else {
            radioRank = 0
        }
        if (radioIndividual.isChecked) {
            radioGrupo = 0
        } else {
            radioGrupo = 1
        }
        if (radioSim.isChecked) {
            radioCorrigir = 0
        } else {
            radioCorrigir = 1
        }
        return Triple(radioGrupo, radioRank, radioCorrigir)
    }

    private fun FragmentNewTaskTeacherBinding.setDate(txtTaskLimitDate2: TextView) {
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, day)
                val format = "yyyy-MM-dd 23:59:59"
                val sdf = SimpleDateFormat(format, Locale.getDefault())
                txtTaskLimitDate2.text = sdf.format(cal.time)
            }
        }
        btndata.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                DatePickerDialog(
                    requireContext(), dateSetListener, cal.get(Calendar.YEAR), cal.get(
                        Calendar.MONTH
                    ), cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        })
    }
}