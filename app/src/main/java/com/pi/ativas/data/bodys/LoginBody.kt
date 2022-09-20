package com.pi.ativas.data.bodys

import com.google.gson.annotations.SerializedName

data class LoginBody(
    @SerializedName("email")
    var email: String,

    @SerializedName("password")
    var password: String,

    @SerializedName("token")
    var token: String
)
