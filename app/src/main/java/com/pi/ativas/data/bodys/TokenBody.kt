package com.pi.ativas.data.bodys

import com.google.gson.annotations.SerializedName

data class TokenBody(
    @SerializedName("email")
    val email: String,

    @SerializedName ("password")
    val password: String
)
