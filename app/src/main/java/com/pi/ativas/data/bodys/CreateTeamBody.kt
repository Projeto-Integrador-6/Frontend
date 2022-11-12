package com.pi.ativas.data.bodys

import com.google.gson.annotations.SerializedName

data class CreateTeamBody (
    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    var password: String,

    @SerializedName("token")
    val token: String,

    @SerializedName("task_id")
    var task_id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("students")
    val students: String
)