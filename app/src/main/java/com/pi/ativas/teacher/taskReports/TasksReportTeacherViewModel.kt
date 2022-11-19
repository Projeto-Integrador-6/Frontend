package com.pi.ativas.teacher.taskReports

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pi.ativas.data.Retrofit
import com.pi.ativas.data.bodys.GetReportsBody
import com.pi.ativas.data.bodys.RequestTaskBody
import com.pi.ativas.data.bodys.RequestTaskTeamsBody
import com.pi.ativas.model.Report
import com.pi.ativas.model.Task
import com.pi.ativas.teacher.model.Team
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TasksReportTeacherViewModel(): ViewModel() {

    private val _listReport = MutableLiveData<List<Report>>()
    val listReport: LiveData<List<Report>> = _listReport

    private val _taskButtonClick = MutableLiveData<Report>()
    val taskButtonClick: LiveData<Report> = _taskButtonClick

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getTaskReports(getReportsBody: GetReportsBody){
        getReports(getReportsBody)
    }

    private fun getReports(getReportsBody: GetReportsBody) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                Retrofit.teacherService.getTaskReports(getReportsBody).let { response ->
                    if (response.isSuccessful){
                        response.body()?.let { getTaskReportsResponse ->
                            Log.i("TESTE", "getReports: TESTE RESPONSE: "+getTaskReportsResponse)
                            _listReport.postValue(getTaskReportsResponse.reports as List<Report>)
                        }
                    }else{
                        _error.postValue(response.raw().code.toString())
                    }
                }
            }
        }
    }
    private val _listTeams = MutableLiveData<List<Team>>()
    val listTeams: LiveData<List<Team>> = _listTeams
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
    fun taskButtonClick(report: Report){
        _taskButtonClick.postValue(report)
    }
}