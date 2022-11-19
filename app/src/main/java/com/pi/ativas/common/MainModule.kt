package com.pi.ativas.common

import com.pi.ativas.firstLogin.NewPasswordViewModel
import com.pi.ativas.login.LoginViewModel
import com.pi.ativas.student.viewmodel.*
import com.pi.ativas.teacher.createGroup.CreateGroupViewModel
import com.pi.ativas.teacher.tasksClassTeacher.TasksClassTeacherViewModel
import com.pi.ativas.teacher.homeTeacher.HomeTeacherViewModel
import com.pi.ativas.teacher.taskTeams.TaskTeamsViewModel
import com.pi.ativas.teacher.newTaskTeacher.NewTaskViewModel
import com.pi.ativas.teacher.profileTeacher.ProfileTeacherViewModel
import com.pi.ativas.teacher.taskReports.TasksReportTeacherViewModel
import com.pi.ativas.teacher.upsetReport.UpsetReportViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    /** Login viewModel flow */
    viewModel { LoginViewModel() }
    viewModel { NewPasswordViewModel() }

    /** Teacher viewModel flow */
    viewModel { HomeTeacherViewModel() }
    viewModel { TasksClassTeacherViewModel() }
    viewModel { TaskTeamsViewModel() }
    viewModel { TasksReportTeacherViewModel() }
    viewModel { UpsetReportViewModel() }

    /** Student viewModel flow */
    viewModel { HomeStudentViewModel() }
    viewModel { NewTaskViewModel() }
    viewModel { ProfileTeacherViewModel() }
    viewModel {CreateGroupViewModel()}
    viewModel { ResponseTaskStudentViewModel() }
    viewModel { RankStudentViewModel() }
    viewModel { DisciplineStudentViewModel() }
    viewModel { TeacherStudentViewModel() }
}