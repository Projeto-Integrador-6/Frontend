package com.pi.ativas.teacher.createGroup

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pi.ativas.MainActivity
import com.pi.ativas.R
import com.pi.ativas.base.BaseFragment
import com.pi.ativas.data.bodys.CreateTeamBody
import com.pi.ativas.data.bodys.StudentsInClassBody
import com.pi.ativas.databinding.FragmentCreateGroupBinding
import com.pi.ativas.model.User
import com.pi.ativas.teacher.model.DataForRequirement
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class CreateGroupFragment : BaseFragment() {
    private lateinit var binding: FragmentCreateGroupBinding
    private lateinit var dataForRequirement: DataForRequirement
    private lateinit var studentsList: List<User>
    private val createGroupViewModel: CreateGroupViewModel by viewModel()
    private lateinit var sharedPreferences: SharedPreferences
    private var listids = mutableSetOf<Int>()
    private var listnames = mutableSetOf<String>()
    private var idaqui = 0
    private var classId = 0
    private var numberOfElements = 0

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
        val activity: MainActivity = activity as MainActivity
        idaqui = activity.getTaskId2()
        classId = activity.getClassId()
        numberOfElements = activity.getTaskLimit()
        binding = FragmentCreateGroupBinding.inflate(layoutInflater)
        initObservers()
        initViews()


        return binding.root
    }

    override fun initViews() {
        with(binding) {

            radioAleatorio.setOnClickListener {
                randomGroup()
            }
            btnCriarEquipe.setOnClickListener {
                if(txtGroupName.text.toString().equals("") || listids.isEmpty()){
                    Toast.makeText(
                        requireContext(),
                        "Favor inserir o nome da equipe e os alunos!",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }else {
                    if(numberOfElements>=txtEquipe.text.toString().split(",").size) {
                        newGroup()
                    }else{
                        Toast.makeText(
                            requireContext(),
                            "Número de alunos é superior ao permitido!",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            }
        }

    }

    private fun FragmentCreateGroupBinding.newGroup() {
        val activity: MainActivity = activity as MainActivity

        val createTeamBody = CreateTeamBody(

            email = dataForRequirement.email,
            password = dataForRequirement.password,
            token = dataForRequirement.token,
            task_id = activity.getTaskId2(),
            name = txtGroupName.text.toString(),
            students = listids.toString().substring(1, listids.toString().length - 1)
        )

        createGroupViewModel.newGroup(createTeamBody)
        txtGroupName.setText("")
        listids.clear()
        listnames.clear()
        val mutableList = mutableListOf<User>()
        studentsList.forEach {
            mutableList.add(it)
        }
        txtEquipe.text.toString().split(", ").forEach {
            val t = it
            studentsList.forEach {
                if (t.equals(it.name)) {
                    mutableList.remove(it)
                }
            }
        }
        studentsList = mutableList.toList()
        Log.i("TESTE", "initViews: MUDOU  " + studentsList)
        txtEquipe.setText("")
        radioGroupGrouped.check(radioManual.id)
        recycleView()

    }

    private fun FragmentCreateGroupBinding.randomGroup() {
        val list = mutableListOf<User>()
        studentsList.forEach {
            list.add(it)
        }
        val rand = Random()
        val newList = mutableSetOf<Int>()
        val newList2 = mutableSetOf<String>()
        Log.i("TESTE", "initViews: LIST SIZE:" + list.size)
        if (numberOfElements <= list.size) {
            for (i in 1..numberOfElements) {

                //listids.add(studentsList.get(rand.nextInt(studentsList.size)).id!!)
                val randomIndex: Int = rand.nextInt(list.size)
                newList.add(list.get(randomIndex).id!!)
                newList2.add(list.get(randomIndex).name)
                list.removeAt(randomIndex)

            }
        } else {
            for (i in 1..list.size) {
                val randomIndex: Int = rand.nextInt(list.size)
                newList.add(list.get(randomIndex).id!!)
                newList2.add(list.get(randomIndex).name)
                list.removeAt(randomIndex)
            }
        }
        for (i in 0..newList.size - 1) {
            listnames.add(newList2.toList().get(i))
            listids.add(newList.toList().get(i))
        }
        newList.clear()
        newList2.clear()

        txtEquipe.setText(
            listnames.toString().substring(1, listnames.toString().length - 1)
        )
    }

    override fun initObservers() {
        Log.i("TESTE", "initObservers: CLASSID:" + classId)
        val studentsInClassBody = StudentsInClassBody(
            email = dataForRequirement.email,
            token = dataForRequirement.token,
            password = dataForRequirement.password,
            class_id = classId,
            type = 1
        )
        createGroupViewModel.getStudents(studentsInClassBody)
        /*newTaskViewModel.id.observe(viewLifecycleOwner) {
            idaqui = it
        }*/
        createGroupViewModel.students.observe(viewLifecycleOwner) {
            studentsList = it
            recycleView()
        }
    }

    private fun recycleView() {
        binding.progressbar.visibility = View.GONE

        val onClickListener = ItemClickListener { user ->
            with(binding) {

                var auxl = 0
                val iteratornames = listnames.iterator()
                val iteratorids = listids.iterator()
                //listnames.add(user.name.toString())
                iteratornames.forEach {
                    if (it.equals(user.name)) {
                        iteratornames.remove()
                        auxl = 1
                    }
                }
                iteratorids.forEach {
                    if (it.equals(user.id)) {
                        iteratorids.remove()
                        auxl = 1
                    }
                }
                if (auxl == 0) {
                    listnames.add(user.name)
                    listids.add(user.id!!)
                }
                Log.i("TESTE", "recycleView: LIST TO SET TO STRING: " + listids)
                txtEquipe.setText(
                    listnames.toString().substring(1, listnames.toString().length - 1)
                )


            }
        }

        val recyclerView = binding.recycleViewHomeTeacher
        val adapter = StudentAdapter(studentsList, onClickListener)
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        if(studentsList.isEmpty()){
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("TODAS AS EQUIPES FORAM FORMADAS COM SUCESSO")
                .setPositiveButton("OK") { dialog, which ->
                //    findNavController().navigate(R.id.newTaskTeacherFragment)
                    findNavController().popBackStack(R.id.homeTeacherFragment,false)
                }
                .show()
        }
    }


}
