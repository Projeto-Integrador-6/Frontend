package com.pi.ativas.common

import com.pi.ativas.firstLogin.NewPasswordViewModel
import com.pi.ativas.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel { LoginViewModel() }
    viewModel { NewPasswordViewModel() }
}