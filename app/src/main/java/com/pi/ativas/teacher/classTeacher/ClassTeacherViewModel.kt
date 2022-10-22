package com.pi.ativas.teacher.classTeacher

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pi.ativas.data.Retrofit
import com.pi.ativas.data.bodys.RequestTaskBody
import com.pi.ativas.model.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ClassTeacherViewModel(): ViewModel() {

    private val _listTask = MutableLiveData<List<Task>>()
    val listTask: LiveData<List<Task>> = _listTask

    fun getClassroomTasks(requestTaskBody: RequestTaskBody){
        getClassroom(requestTaskBody)
    }

    private fun getClassroom(requestTaskBody: RequestTaskBody) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                Retrofit.teacherService.getClassroomTasks(requestTaskBody).let { response ->
                    response.body()?.let { requestTaskResponse ->
                        _listTask.postValue(requestTaskResponse.content as List<Task>)
                    }
                }
            }
        }
    }
}