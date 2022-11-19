package com.pi.ativas.teacher.model

import android.os.Parcelable
import android.provider.ContactsContract.Data
import com.pi.ativas.data.bodys.RequestClassroomBody
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PontuationReport(
    var pontuation: Int,
    var correct: Int
    ): Parcelable {
}
