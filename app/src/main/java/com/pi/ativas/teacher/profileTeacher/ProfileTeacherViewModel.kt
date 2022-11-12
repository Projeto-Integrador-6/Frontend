package com.pi.ativas.teacher.profileTeacher

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pi.ativas.data.Retrofit
import com.pi.ativas.data.bodys.LoginBody
import com.pi.ativas.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileTeacherViewModel(): ViewModel() {

    private val _profileTeacher = MutableLiveData<User>()
    val profileTeacher: LiveData<User> = _profileTeacher


    fun getProfile(loginBody: LoginBody){
        setNewLoginTeacher(loginBody)
    }

    private fun setNewLoginTeacher(loginBody: LoginBody) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                Retrofit.loginApiTeacher.getLogin(loginBody).let { response ->
                    response.body()?.let { loginResponseBody ->
                        _profileTeacher.postValue(loginResponseBody?.user as User)
                    }
                }
            }
        }
    }

}