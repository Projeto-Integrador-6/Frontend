package com.pi.ativas.teacher.taskTeams

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

    fun getTaskTeams(requestTaskTeamsBody: RequestTaskTeamsBody) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                Retrofit.teacherService.getTaskTeams(requestTaskTeamsBody).let { response ->
                    response.body()?.let { requestTaskTeamsResponse ->
                        _listTeams.postValue(requestTaskTeamsResponse.content as List<Team>)
                    }
                }
            }
        }
    }
}