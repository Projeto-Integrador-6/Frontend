package com.pi.ativas.teacher.newTaskTeacher

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pi.ativas.MainActivity
import com.pi.ativas.R
import com.pi.ativas.base.BaseFragment
import com.pi.ativas.data.bodys.InsertTaskBody
import com.pi.ativas.databinding.FragmentNewTaskTeacherBinding
import com.pi.ativas.model.Classroom
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
    private lateinit var classroom: Classroom
    private var radioGrupo2: Int = 0

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
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).setTittleAppBar("Criar Atividade")

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
                    classroom = binding.spnClass.selectedItem as Classroom
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
            })
            newTaskViewModel.success.observe(viewLifecycleOwner) {
                binding.progressbar.visibility = View.GONE
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("NOVA ATIVIDADE CRIADA COM SUCESSO")
                    .setPositiveButton("OK") { dialog, which ->
                        if (radioGrupo2 == 1) {
                            findNavController().navigate(R.id.action_newTaskTeacher_to_createGroup)
                        }
                        else {
                            binding.txtTaskTitle.setText("")
                            binding.txtTaskAnswer.setText("")
                            binding.txtTaskDescription.setText("")
                            binding.txtTaskLimitDate.setText("")
                            binding.txtTaksReductionFactor.setText("")
                            binding.txtTaskPontuation.setText("")
                        }

                    }
                    .show()
            }


        }

        newTaskViewModel.error.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), "N??o foi possivel criar a atividade! Erro $it", Toast.LENGTH_SHORT).show()
            binding.progressbar.visibility=View.GONE
        }

    }

    override fun initViews() {
        with(binding) {
            var radioRank: Int = 0
            val txtTaskLimitDate2: TextView = txtTaskLimitDate
            radioEmGrupo.setOnClickListener {
                txtMemberLimit.visibility = View.VISIBLE
                radioGrupo2 = 1
            }
            radioIndividual.setOnClickListener {
                txtMemberLimit.visibility = View.GONE
                radioGrupo2 = 0
            }
            radioSimRank.setOnClickListener {
                radioRank = 1
            }
            radioNaoRank.setOnClickListener {
                radioRank = 0
            }
            var radioCorrigir: Int = 1

            radioSim.setOnClickListener {
                radioCorrigir = 1
            }
            radioNao.setOnClickListener {
                radioCorrigir = 0
            }
            setDate(txtTaskLimitDate2)
            spnClass.adapter
            btnCriarAtividade.setOnClickListener {
               // val groupNumberValid = if (radioGrupo2 == 1) !txtMemberLimit.text.isNullOrEmpty() else true

                if ( txtTaskTitle.equals("") || txtTaskDescription.text.toString()
                        .equals("") || txtTaskLimitDate.text.toString().equals("")
                    || txtTaksReductionFactor.text.toString()
                        .equals("") || txtTaskPontuation.equals("")
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Favor inserir todos os valores obrigat??rios!",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    var memberInt = 1
                    if (!txtMemberLimit.text.toString().equals("")) {
                        memberInt = txtMemberLimit.text.toString().toInt()
                    }
                    val insertTaskBody = InsertTaskBody(
                        email = dataForRequirement.email,
                        password = dataForRequirement.password,
                        token = dataForRequirement.token,
                        class_id = classroom.id,
                        grouped = radioGrupo2,
                        rank = radioRank,
                        limit_date = txtTaskLimitDate2.text.toString(),
                        pontuation = txtTaskPontuation.text.toString().toInt(),
                        answer = txtTaskAnswer.text.toString(),
                        question_title = txtTaskTitle.text.toString(),
                        question = txtTaskDescription.text.toString(),
                        correction = radioCorrigir,
                        file = "dsadas",
                        member_limit = memberInt,
                        reduction_factor = txtTaksReductionFactor.text.toString().toFloat()
                    )
                    if (radioGrupo2 == 1) {
                        newTaskGroup(insertTaskBody, memberInt)
                    } else {
                        insertNewTask(insertTaskBody, memberInt)
                    }
                }
            }
        }
    }

    private fun newTaskGroup(
        insertTaskBody: InsertTaskBody,
        memberInt: Int
    ) {
        binding.progressbar.visibility = View.VISIBLE
        binding.progressbar.bringToFront()
        val activity: MainActivity = activity as MainActivity
        newTaskViewModel.newTask(insertTaskBody, activity, memberInt)
    }

    private fun FragmentNewTaskTeacherBinding.insertNewTask(
        insertTaskBody: InsertTaskBody,
        memberInt: Int
    ) {
       binding.progressbar.visibility = View.VISIBLE
        binding.progressbar.bringToFront()

        val activity: MainActivity = activity as MainActivity
        newTaskViewModel.newTask(insertTaskBody, activity, memberInt)
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