package com.pi.ativas.student.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pi.ativas.data.Retrofit
import com.pi.ativas.data.bodys.LoginBody
import com.pi.ativas.model.Discipline
import kotlinx.coroutines.launch

class DisciplineStudentViewModel : ViewModel() {

    private val _listDisciplines = MutableLiveData<List<Discipline>>()
    val listDisciplines: LiveData<List<Discipline>> = _listDisciplines

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getDisciplines(loginBody: LoginBody) {
        viewModelScope.launch {
            Retrofit.studentService.getDisciplines(loginBody).let { response ->
                if (response.isSuccessful) {
                    response.body()?.let { getDisciplinesResponse ->
                        _listDisciplines.postValue(getDisciplinesResponse.content!!)
                    }
                } else {
                    _error.postValue(response.raw().code.toString())
                }
            }
        }
    }
}