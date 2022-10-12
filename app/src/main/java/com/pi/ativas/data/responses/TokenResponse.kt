package com.pi.ativas.data.responses

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("token")
    val token: String,

    @SerializedName("checkCredencials")
    val checkCredentials: Boolean,

    @SerializedName("inactiveAccount")
    val inactiveAccount: Boolean
)
