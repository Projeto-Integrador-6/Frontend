package com.pi.ativas.teacher.homeTeacher

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pi.ativas.data.Retrofit
import com.pi.ativas.data.bodys.RequestClassroomBody
import com.pi.ativas.teacher.model.Classroom
import com.pi.ativas.teacher.model.DataForRequirement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeTeacherViewModel(): ViewModel() {

    private val _listClassroom = MutableLiveData<List<Classroom>>()
    val listClassroom: LiveData<List<Classroom>> = _listClassroom

    fun getClassroom(dataForRequirement: DataForRequirement){
        getClassroom(dataForRequirement.toRequestClassroomBody())
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