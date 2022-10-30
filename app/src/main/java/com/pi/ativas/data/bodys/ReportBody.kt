package com.pi.ativas.data.bodys

import android.util.Base64
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

    @SerializedName("team_id")
    var teamId: Int

)
