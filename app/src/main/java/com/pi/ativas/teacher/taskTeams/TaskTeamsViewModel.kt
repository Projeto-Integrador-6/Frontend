package com.pi.ativas.teacher.taskTeams

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pi.ativas.data.Retrofit
import com.pi.ativas.data.bodys.RequestTaskTeamsBody
import com.pi.ativas.teacher.model.Team
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskTeamsViewModel : ViewModel() {

    private val _listTeams = MutableLiveData<List<Team>>()
    val listTeams: LiveData<List<Team>> = _listTeams

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getTaskTeams(requestTaskTeamsBody: RequestTaskTeamsBody) {
        viewModelScope.launch {
            Retrofit.teacherService.getTaskTeams(requestTaskTeamsBody).let { response ->
                if (response.isSuccessful) {
                    response.body()?.let { requestTaskTeamsResponse ->
                    Log.i("TESTE", "getTaskTeams: RESPONSe "+requestTaskTeamsResponse)
                        _listTeams.postValue(requestTaskTeamsResponse.content as List<Team>)
                    }
                } else {
                    _error.postValue(response.raw().code.toString())
                }
            }
        }
    }
}