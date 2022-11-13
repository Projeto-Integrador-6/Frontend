package com.pi.ativas.student.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pi.ativas.data.Retrofit
import com.pi.ativas.data.bodys.GetTeachersBody
import com.pi.ativas.model.Teacher
import kotlinx.coroutines.launch

class TeacherStudentViewModel: ViewModel() {
    private val _response = MutableLiveData<List<Teacher>>()
    val response: LiveData<List<Teacher>> = _response

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getTeachers(getTeachersBody: GetTeachersBody) {
        viewModelScope.launch {
            Retrofit.studentService.getTeachers(getTeachersBody).let { response ->
                if (response.isSuccessful) {
                    response.body()?.let { getTeachersResponse ->
                        _response.postValue(getTeachersResponse.content!!)
                    }
                } else {
                    _error.postValue(response.raw().code.toString())
                }
            }
        }
    }
}