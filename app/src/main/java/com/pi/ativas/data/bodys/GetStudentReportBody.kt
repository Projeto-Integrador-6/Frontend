package com.pi.ativas.data.bodys

import com.google.gson.annotations.SerializedName

data class GetStudentReportBody(
    @SerializedName("email")
    var email: String,

    @SerializedName("password")
    var password: String,

    @SerializedName("token")
    var token: String,

    @SerializedName("type")
    var type: Int,


)
