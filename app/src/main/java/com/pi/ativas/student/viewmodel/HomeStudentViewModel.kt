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

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _tokenInvalid = MutableLiveData<Boolean>()
    val tokenInvalid: LiveData<Boolean> = _tokenInvalid

    fun getTask(requestTaskBody: RequestTaskBody) {
        viewModelScope.launch {
            Retrofit.studentService.getTasks(requestTaskBody).let { response ->
                if (response.isSuccessful) {
                    response.body()?.let { requestTaskResponse ->
                        if (requestTaskResponse.success) {
                            _listTask.postValue(requestTaskResponse.content as List<Task>)
                        } else {
                            _tokenInvalid.postValue(requestTaskResponse.generateToken == true)
                        }
                    }
                } else {
                    _error.postValue(response.raw().code.toString())
                }
            }
        }
    }
}
