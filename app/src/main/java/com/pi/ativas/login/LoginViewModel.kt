package com.pi.ativas.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pi.ativas.data.Retrofit
import com.pi.ativas.data.bodys.LoginBody
import com.pi.ativas.data.bodys.TokenBody
import com.pi.ativas.data.responses.LoginResponse
import com.pi.ativas.data.responses.TokenResponse
import com.pi.ativas.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel() : ViewModel() {

    private val _invalidCredential = MutableLiveData<Boolean>()
    val invalidCredential: LiveData<Boolean> = _invalidCredential

    private val _inactiveAccount = MutableLiveData<Boolean>()
    val inactiveAccount: LiveData<Boolean> = _inactiveAccount

    private val _newPassword = MutableLiveData<Boolean>()
    val newPassword: LiveData<Boolean> = _newPassword

    private val _dataLogin = MutableLiveData<LoginBody>()
    val dataLogin: LiveData<LoginBody> = _dataLogin

    // OBSERVAVEIS APENAS PARA MOSTRAR OQUE ESTÁ VOLTANDO DA API
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private var credentialsLogin: LoginBody = LoginBody("", "", "")
    private var isStudent: Boolean = true

    fun newLogin(email: String, password: String) {
        with(credentialsLogin) {
            this.email = email
            this.password = password
            setNewLoginStudent(this)
        }
    }

    private fun newLogin(tokenResponse: TokenResponse) {
        credentialsLogin.token = tokenResponse.token
        getNewLogin()
    }

    private fun checkLogin(loginResponse: LoginResponse) {
        with(loginResponse) {
            checkCredencials?.let {
                checkCredencials(it)
            }
            generateToken?.let {
                getNewToken()
            }
            inactiveAccount?.let {
                _inactiveAccount.postValue(it)
            }
            changePassword?.let {
                _dataLogin.postValue(credentialsLogin)
                _newPassword.postValue(it)
            }
            user?.let {
                _user.postValue(it)
            }
        }
    }

    private fun checkCredencials(it: Boolean) {
        if (it && isStudent) {
            isStudent = false
            setNewLoginTeacher(credentialsLogin)
        } else {
            isStudent = true
            _invalidCredential.postValue(it)
        }
    }

    private fun getNewToken() {
        TokenBody(credentialsLogin.email, credentialsLogin.password).let {
            if (isStudent) {
                setNewTokenStudent(it)
            } else {
                setNewTokenTeacher(it)
            }
        }
    }

    private fun getNewLogin() {
        if (isStudent) {
            setNewLoginStudent(credentialsLogin)
        } else {
            setNewLoginTeacher(credentialsLogin)
        }
    }

    private fun setNewLoginStudent(loginBody: LoginBody) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                Retrofit.loginApiStudent.getLogin(loginBody).let { response ->
                    response.body()?.let { loginResponseBody ->
                        checkLogin(loginResponseBody)
                    }
                }
            }
        }
    }

    private fun setNewLoginTeacher(loginBody: LoginBody) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                Retrofit.loginApiTeacher.getLogin(loginBody).let { response ->
                    response.body()?.let { loginResponseBody ->
                        checkLogin(loginResponseBody)
                    }
                }
            }
        }
    }

    private fun setNewTokenStudent(tokenBody: TokenBody) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                Retrofit.tokenApiStudent.getNewToken(tokenBody).let { response ->
                    response.body()?.let { tokenResponseBody ->
                        newLogin(tokenResponseBody)
                    }
                }
            }
        }
    }

    private fun setNewTokenTeacher(tokenBody: TokenBody) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                Retrofit.tokenApiTeacher.getNewToken(tokenBody).let { response ->
                    response.body()?.let { tokenResponseBody ->
                        newLogin(tokenResponseBody)
                    }
                }
            }
        }
    }

}