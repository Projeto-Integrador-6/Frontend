package com.pi.ativas.student.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pi.ativas.data.Retrofit
import com.pi.ativas.data.bodys.ReportBody
import kotlinx.coroutines.launch

class ResponseTaskStudentViewModel : ViewModel() {

    private val _response = MutableLiveData<String>()
    val response: LiveData<String> = _response

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun setResponse(reportBody: ReportBody) {
        viewModelScope.launch {
            Retrofit.studentService.postReport(reportBody).let { response ->
                if (response.isSuccessful) {
                    response.body()?.let { reportResponse ->
                        if (reportResponse.success) {
                            _response.postValue(reportResponse.message)
                        } else {
                            _error.postValue(reportResponse.message)
                        }
                    }
                } else {
                    _error.postValue(response.raw().code.toString())
                }
            }
        }
    }
}