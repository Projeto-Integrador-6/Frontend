package com.pi.ativas.data.bodys

import com.google.gson.annotations.SerializedName

data class StudentsInTeamBody (
    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    var password: String,

    @SerializedName("token")
    val token: String,

    @SerializedName("teamId")
    val teamId: String,

)
