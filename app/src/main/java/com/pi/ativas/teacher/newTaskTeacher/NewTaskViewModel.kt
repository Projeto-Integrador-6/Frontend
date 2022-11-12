package com.pi.ativas.teacher.newTaskTeacher

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pi.ativas.data.Retrofit
import com.pi.ativas.data.bodys.InsertTaskBody
import com.pi.ativas.data.bodys.NewPasswordBody
import com.pi.ativas.data.bodys.RequestClassroomBody
import com.pi.ativas.data.responses.InsertTaskResponse
import com.pi.ativas.data.responses.NewPasswordResponse
import com.pi.ativas.model.Classroom
import com.pi.ativas.teacher.model.DataForRequirement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewTaskViewModel(): ViewModel() {

    private val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> = _success
    private val _listClassroom = MutableLiveData<List<Classroom>>()
    val listClassroom: LiveData<List<Classroom>> = _listClassroom

    private val _success1 = MutableLiveData<Boolean>()
    val success1: LiveData<Boolean> = _success1

    fun newTask(insertTaskBody: InsertTaskBody) {
        setTask(insertTaskBody)
    }

    fun getClassroom(dataForRequirement: DataForRequirement){
        getClassroom(dataForRequirement.toRequestClassroomBody())
    }

    private fun checkInsertTask(insertTaskResponse: InsertTaskResponse) {
        with(insertTaskResponse) {
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
    }

    private fun setTask(insertTaskBody: InsertTaskBody) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                Retrofit.teacherService.getInsertTask(insertTaskBody).let { response ->
                    response.body()?.let { insertTaskResponse ->
                        checkInsertTask(insertTaskResponse)
                    }
                }
            }
        }
    }

    private fun getClassroom(requestClassroomBody: RequestClassroomBody) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                Retrofit.teacherService.getClassroom(requestClassroomBody).let { response ->
                    response.body()?.let { requestClassroomResponse ->
                        _listClassroom.postValue(requestClassroomResponse.content as List<Classroom>)
                    }
                }
            }
        }
    }

}