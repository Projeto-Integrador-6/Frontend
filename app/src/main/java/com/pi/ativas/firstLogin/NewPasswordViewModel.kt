package com.pi.ativas.firstLogin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pi.ativas.data.Retrofit
import com.pi.ativas.data.bodys.NewPasswordBody
import com.pi.ativas.data.responses.NewPasswordResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewPasswordViewModel(): ViewModel() {

    private val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> = _success

    // OBSERVAVEIS APENAS PARA MOSTRAR OQUE ESTÁ VOLTANDO DA API

    private val _success1 = MutableLiveData<Boolean>()
    val success1: LiveData<Boolean> = _success1

    fun newPassword(newPasswordBody: NewPasswordBody) {
        setNewPasswordStudent(newPasswordBody)
    }


    private fun checkNewPassword(newPasswordResponse: NewPasswordResponse) {
        with(newPasswordResponse) {
            success.let {
                if (it) _success.postValue(it)
            }

            checkCredentials?.let {
                _success1.postValue(it)
            }
            generateToken?.let {
                _success1.postValue(it)
            }
            validParameters.let {
                _success1.postValue(it)
            }
            weakPassword?.let {
                _success1.postValue(it)
            }

        }
    }

    private fun setNewPasswordStudent(newPasswordBody: NewPasswordBody) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                Retrofit.loginApiStudent.getNewPassword(newPasswordBody).let { response ->
                    response.body()?.let { newPasswordResponse ->
                        checkNewPassword(newPasswordResponse)
                    }
                }
            }
        }
    }

    private fun setNewPasswordTeacher() {

    }
}