package com.pi.ativas.data.bodys

import com.google.gson.annotations.SerializedName

data class RequestRankingBody(
    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    var password: String,

    @SerializedName("token")
    val token: String,

    @SerializedName("classId")
    var classId: Int? = null
)
