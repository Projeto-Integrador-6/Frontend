package com.pi.ativas.teacher.upsetReport

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pi.ativas.data.Retrofit
import com.pi.ativas.data.bodys.RequestTaskTeamsBody
import com.pi.ativas.data.bodys.StudentsInTeamBody
import com.pi.ativas.data.bodys.UpsetReportsBody
import com.pi.ativas.model.User
import com.pi.ativas.teacher.model.Team
import kotlinx.coroutines.launch

class UpsetReportViewModel : ViewModel() {
    private val _listTeams = MutableLiveData<List<Team>>()
    val listTeams: LiveData<List<Team>> = _listTeams

    private val _listStudents = MutableLiveData<List<User>>()
    val listStudents: LiveData<List<User>> = _listStudents

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getTeams(requestTaskTeamsBody: RequestTaskTeamsBody) {
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

    fun getStudentsInTeam(studentsInTeamBody: StudentsInTeamBody) {
        viewModelScope.launch {
            Retrofit.teacherService.getStudentsInTeam(studentsInTeamBody).let { response ->
                if (response.isSuccessful) {
                    response.body()?.let { studentsInTeamResponse ->
                        Log.i("TESTE", "getTaskTeams: RESPONSe "+studentsInTeamResponse)
                        _listStudents.postValue(studentsInTeamResponse.content as List<User>)
                    }
                } else {
                    _error.postValue(response.raw().code.toString())
                }
            }
        }
    }
    fun upsetReport(upsetReportsBody: UpsetReportsBody) {
        viewModelScope.launch {
            Retrofit.teacherService.getUpserReport(upsetReportsBody).let { response ->
                    response.body()?.let { upsetResponse ->
                        Log.i("TESTE", "upsetReport: RESPONSe "+upsetResponse)
                    }
            }
        }
    }

}