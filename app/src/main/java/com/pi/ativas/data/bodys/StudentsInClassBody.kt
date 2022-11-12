package com.pi.ativas.data.bodys

import com.google.gson.annotations.SerializedName

data class StudentsInClassBody (
    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    var password: String,

    @SerializedName("token")
    val token: String,

    @SerializedName("class_id")
    val class_id: Int,

    @SerializedName("type")
    val type: Int,
)
