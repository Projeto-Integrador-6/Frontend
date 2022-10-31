package com.pi.ativas.common

import com.pi.ativas.firstLogin.NewPasswordViewModel
import com.pi.ativas.login.LoginViewModel
import com.pi.ativas.teacher.classTeacher.ClassTeacherViewModel
import com.pi.ativas.teacher.homeTeacher.HomeTeacherViewModel
import com.pi.ativas.teacher.newTaskTeacher.NewTaskViewModel
import com.pi.ativas.teacher.profileTeacher.ProfileTeacherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel { LoginViewModel() }
    viewModel { NewPasswordViewModel() }
    viewModel { HomeTeacherViewModel() }
    viewModel { ClassTeacherViewModel() }
    viewModel {NewTaskViewModel()}
    viewModel { ProfileTeacherViewModel() }
}