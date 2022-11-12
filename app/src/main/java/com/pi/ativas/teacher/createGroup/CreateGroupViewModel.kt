package com.pi.ativas.teacher.createGroup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pi.ativas.MainActivity
import com.pi.ativas.data.Retrofit
import com.pi.ativas.data.bodys.CreateTeamBody
import com.pi.ativas.data.bodys.InsertTaskBody
import com.pi.ativas.data.bodys.LoginBody
import com.pi.ativas.data.bodys.StudentsInClassBody
import com.pi.ativas.data.responses.CreateTeamResponse
import com.pi.ativas.data.responses.InsertTaskResponse
import com.pi.ativas.model.Classroom
import com.pi.ativas.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateGroupViewModel : ViewModel() {
    private val _students = MutableLiveData<List<User>>()
    val students: LiveData<List<User>> = _students
    private val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> = _success
    private val _listClassroom = MutableLiveData<List<Classroom>>()
    val listClassroom: LiveData<List<Classroom>> = _listClassroom

    private val _success1 = MutableLiveData<Boolean>()
    val success1: LiveData<Boolean> = _success1

    fun getStudents(studentsInClassBody: StudentsInClassBody){
        getStudentsInClass(studentsInClassBody)
    }

    private fun checkInsertTask(createTeamResponse: CreateTeamResponse) {
        with(createTeamResponse) {
            success.let {
                if (it) _success.postValue(it)
            }

            checkCredentials?.let {
                _success1.postValue(it)
            }
            generateToken?.let {
                _success1.postValue(it)
            }
            inactiveAccount.let {
                _success1.postValue(it)
            }

        }
    }fun newGroup(createTeamBody: CreateTeamBody) {
        setGroup(createTeamBody)
    }
    private fun setGroup(createTeamBody: CreateTeamBody) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                Retrofit.teacherService.getCreateTeam(createTeamBody).let { response ->
                    response.body()?.let { createTeamResponse ->
                        checkInsertTask(createTeamResponse)
                        Log.i("TESTE", "setGroup: "+createTeamResponse)
                    }
                }
            }
        }
    }
    private fun getStudentsInClass(studentsInClassBody: StudentsInClassBody) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                Retrofit.teacherService.getStudentsInClass(studentsInClassBody).let { response ->
                    response.body()?.let { studentsInClassResponse ->
                        _students.postValue(studentsInClassResponse?.content as List<User>)
                    }
                }
            }
        }
    }}