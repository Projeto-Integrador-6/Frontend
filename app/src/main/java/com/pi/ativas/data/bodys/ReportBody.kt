package com.pi.ativas.data.bodys

import com.google.gson.annotations.SerializedName

data class ReportBody(
    @SerializedName("email")
    var email: String,

    @SerializedName("password")
    var password: String,

    @SerializedName("token")
    var token: String,

    @SerializedName("data")
    var date: String,

    @SerializedName("answer")
    var answer: String,

    @SerializedName("anexo")
    var anexo: String?,

    @SerializedName("task_id")
    var teamId: Int

)
