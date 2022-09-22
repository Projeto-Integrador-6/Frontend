package com.pi.ativas.data.bodys

import com.google.gson.annotations.SerializedName

data class NewPasswordBody(
    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    var password: String,

    @SerializedName("token")
    val token: String,

    @SerializedName("oldPassword")
    val oldPassword: String,

    @SerializedName("newPassword")
    val newPassword: String
)