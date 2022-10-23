package com.pi.ativas.student.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pi.ativas.data.Retrofit
import com.pi.ativas.data.bodys.RequestTaskBody
import com.pi.ativas.model.Task
import kotlinx.coroutines.launch

class HomeStudentViewModel : ViewModel() {

    private val _listTask = MutableLiveData<List<Task>>()
    val listTask: LiveData<List<Task>> = _listTask

    fun getTask(requestTaskBody: RequestTaskBody) {
        viewModelScope.launch {
            Retrofit.studentService.getTasks(requestTaskBody).let { response ->
                response.body()?.let { requestTaskResponse ->
                    _listTask.postValue(requestTaskResponse.content as List<Task>)
                }
            }
        }
    }
}
