package com.pi.ativas.teacher.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Classroom(
    val id: Int,
    val name: String,
    val cod: Int

): Parcelable {
    override fun toString(): String {
        return name
    }
}
