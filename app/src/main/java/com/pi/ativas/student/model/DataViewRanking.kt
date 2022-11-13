package com.pi.ativas.student.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataViewRanking(
    val studentName: String,
    val pontuation: String,
    val porcentage: String,
    val numberTasks: String,
    val numberStudents: String,
    val studentPosition: String
) : Parcelable
