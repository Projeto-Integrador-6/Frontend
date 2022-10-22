package com.pi.ativas.teacher.tasksClassTeacher

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

class TasksClassTeacherViewModel(): ViewModel() {

    private val _listTask = MutableLiveData<List<Task>>()
    val listTask: LiveData<List<Task>> = _listTask

    private val _taskButtonClick = MutableLiveData<Task>()
    val taskButtonClick: LiveData<Task> = _taskButtonClick

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

    fun taskButtonClick(task: Task){
        _taskButtonClick.postValue(task)
    }
}