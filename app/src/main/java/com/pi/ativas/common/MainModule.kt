package com.pi.ativas.common

import com.pi.ativas.firstLogin.NewPasswordViewModel
import com.pi.ativas.login.LoginViewModel
import com.pi.ativas.teacher.tasksClassTeacher.TasksClassTeacherViewModel
import com.pi.ativas.teacher.homeTeacher.HomeTeacherViewModel
import com.pi.ativas.teacher.taskTeams.TaskTeamsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel { LoginViewModel() }
    viewModel { NewPasswordViewModel() }
    viewModel { HomeTeacherViewModel() }
    viewModel { TasksClassTeacherViewModel() }
    viewModel { TaskTeamsViewModel() }
}