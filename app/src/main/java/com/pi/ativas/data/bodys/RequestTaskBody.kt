package com.pi.ativas.data.bodys

import com.google.gson.annotations.SerializedName

data class RequestTaskBody(
    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("token")
    val token: String,

    @SerializedName("classId")
    val classId: Int,

    @SerializedName("taskType")
    val taskType: Int?

)
