package com.pi.ativas.teacher.homeTeacher

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pi.ativas.data.Retrofit
import com.pi.ativas.data.bodys.RequestClassroomBody
import com.pi.ativas.model.Classroom
import com.pi.ativas.teacher.model.DataForRequirement
import kotlinx.coroutines.launch

class HomeTeacherViewModel() : ViewModel() {

    private val _listClassroom = MutableLiveData<List<Classroom>>()
    val listClassroom: LiveData<List<Classroom>> = _listClassroom

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _tokenInvalid = MutableLiveData<Boolean>()
    val tokenInvalid: LiveData<Boolean> = _tokenInvalid

    fun getClassroom(dataForRequirement: DataForRequirement) {
        getClassroom(dataForRequirement.toRequestClassroomBody())
    }

    fun getClassroom2(requestClassroomBody: RequestClassroomBody) {
        getClassroom(requestClassroomBody)
    }

    private fun getClassroom(requestClassroomBody: RequestClassroomBody) {
        viewModelScope.launch {
            Retrofit.teacherService.getClassroom(requestClassroomBody).let { response ->
                if (response.isSuccessful) {
                    response.body()?.let { requestClassroomResponse ->
                        if (requestClassroomResponse.success) {
                            _listClassroom?.postValue(requestClassroomResponse?.content as List<Classroom>)
                            Log.i("TESTE", "getClassroom: " + requestClassroomResponse)
                            Log.i("TESTE", "getClassroom: " + requestClassroomBody)
                        } else {
                            val x = (requestClassroomResponse.generateToken == true)
                            _tokenInvalid.postValue(x)
                        }
                    }
                } else {
                    _error.postValue(response.raw().code.toString())
                }
            }
        }
    }
}