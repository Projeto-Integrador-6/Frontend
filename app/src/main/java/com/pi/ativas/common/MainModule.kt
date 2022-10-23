package com.pi.ativas.common

import com.pi.ativas.firstLogin.NewPasswordViewModel
import com.pi.ativas.login.LoginViewModel
import com.pi.ativas.student.viewmodel.HomeStudentViewModel
import com.pi.ativas.teacher.classTeacher.ClassTeacherViewModel
import com.pi.ativas.teacher.homeTeacher.HomeTeacherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    /** Login viewModel flow */
    viewModel { LoginViewModel() }
    viewModel { NewPasswordViewModel() }

    /** Teacher viewModel flow */
    viewModel { HomeTeacherViewModel() }
    viewModel { ClassTeacherViewModel() }

    /** Student viewModel flow */
    viewModel { HomeStudentViewModel()}
}