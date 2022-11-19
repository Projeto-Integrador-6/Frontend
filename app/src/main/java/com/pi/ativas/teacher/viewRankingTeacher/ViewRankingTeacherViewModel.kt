package com.pi.ativas.teacher.viewRankingTeacher

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pi.ativas.data.Retrofit
import com.pi.ativas.data.bodys.RequestRankingBody
import com.pi.ativas.data.responses.RequestRankingResponse
import kotlinx.coroutines.launch

class ViewRankingTeacherViewModel: ViewModel() {

    private val _response = MutableLiveData<RequestRankingResponse>()
    val response: LiveData<RequestRankingResponse> = _response

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getRanking(requestRankingBody: RequestRankingBody) {

        Log.i("RequestRankingBody", requestRankingBody.toString())

        viewModelScope.launch {
            Retrofit.teacherService.requestRanking(requestRankingBody).let { response ->

                Log.i("RequestRankingResponseSuccess", response.isSuccessful.toString())
                Log.i("RequestRankingResponseBody", response.body().toString())

                if (response.isSuccessful) {
                    response.body()?.let { reportResponse ->
                        if (reportResponse.success) {
                            _response.postValue(reportResponse)
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