package com.pi.ativas.student.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pi.ativas.data.Retrofit
import com.pi.ativas.data.bodys.RequestRankingBody
import com.pi.ativas.model.RankingStudent
import kotlinx.coroutines.launch

class RankStudentViewModel: ViewModel() {

    private val _response = MutableLiveData<List<RankingStudent>>()
    val response: LiveData<List<RankingStudent>> = _response

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getRanking(requestRankingBody: RequestRankingBody) {

        Log.i("RequestRankingBody", requestRankingBody.toString())

        viewModelScope.launch {
            Retrofit.studentService.requestRanking(requestRankingBody).let { response ->

                Log.i("RequestRankingResponseSuccess", response.isSuccessful.toString())
                Log.i("RequestRankingResponseBody", response.body().toString())

                if (response.isSuccessful) {
                    response.body()?.let { reportResponse ->
                        if (reportResponse.success) {
                            _response.postValue(reportResponse.content)
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