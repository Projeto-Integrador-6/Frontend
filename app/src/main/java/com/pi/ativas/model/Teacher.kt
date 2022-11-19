package com.pi.ativas.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Teacher(
    val classId: Int,
    val name: String,
    val email: String,
    val registration: String,
    val lattes: String,
    val discipline_name: String
) : Parcelable
