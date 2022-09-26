package com.pi.ativas.data.responses

import com.google.gson.annotations.SerializedName

data class NewPasswordResponse(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("checkCredencials")
    val checkCredencials: Boolean?,

    @SerializedName("generateToken")
    val generateToken: Boolean?,

    @SerializedName("validParameters")
    val validParameters: Boolean?,

    @SerializedName("weakPassword")
    val weakPassword: Boolean?
)
