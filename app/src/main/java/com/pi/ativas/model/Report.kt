package com.pi.ativas.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.sql.Blob

@Parcelize
data class Report(
    val id: Int?,
    val pontuation: Int?,
    val data: String?,
    val correct:Int?,
    val answer:String?,
    val task_id: Int?,
    val team_id: Int?,
    val anexo: String?

): Parcelable {
}
