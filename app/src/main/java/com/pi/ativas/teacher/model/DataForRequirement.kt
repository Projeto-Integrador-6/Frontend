package com.pi.ativas.teacher.model

import android.os.Parcelable
import android.provider.ContactsContract.Data
import com.pi.ativas.data.bodys.RequestClassroomBody
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataForRequirement(
    var email: String,
    var password: String,
    var token: String
): Parcelable {
    fun toRequestClassroomBody(): RequestClassroomBody{
        return RequestClassroomBody(this.email, this.password, this.token)
    }
}
