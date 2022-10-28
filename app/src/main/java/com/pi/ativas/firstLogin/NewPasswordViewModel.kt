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

class NewPasswordViewModel() : ViewModel() {

    private val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> = _success

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun newPasswordStudent(newPasswordBody: NewPasswordBody) {
        setNewPasswordStudent(newPasswordBody)
    }

    fun newPasswordTeacher(newPasswordBody: NewPasswordBody) {
        setNewPasswordTeacher(newPasswordBody)
    }

    private fun checkNewPassword(newPasswordResponse: NewPasswordResponse) {
        with(newPasswordResponse) {
            success.let {
                if (it) _success.postValue(it)
            }

            checkCredentials?.let {
                _error.postValue("CREDENCIAS ERRADAS")
            }
            generateToken?.let {
                _error.postValue("TOKEN INVALIDO")
            }
            validParameters.let {
                _error.postValue("PARAMETROS INVALIDOS")
            }
            weakPassword?.let {
                _error.postValue("SENHA INVALIDA")
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

    private fun setNewPasswordTeacher(newPasswordBody: NewPasswordBody) {
        viewModelScope.launch {
            Retrofit.loginApiTeacher.getNewPassword(newPasswordBody).let { response ->
                response.body()?.let { newPasswordResponse ->
                    checkNewPassword(newPasswordResponse)
                }
            }
        }
    }
}