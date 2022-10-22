package com.pi.ativas.data.bodys

import com.google.gson.annotations.SerializedName

data class RequestTaskTeamsBody(
    @SerializedName("email")
    var email: String,

    @SerializedName("password")
    var password: String,

    @SerializedName("token")
    var token: String,

    @SerializedName("taskId")
    var taskId: String
)
