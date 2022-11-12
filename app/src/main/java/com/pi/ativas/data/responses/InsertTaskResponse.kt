package com.pi.ativas.data.responses

import com.google.gson.annotations.SerializedName
import com.pi.ativas.model.Task

data class InsertTaskResponse(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("checkCredencials")
    val checkCredentials: Boolean?,

    @SerializedName("generateToken")
    val generateToken: Boolean?,

    @SerializedName("inactiveAccount")
    val inactiveAccount: Boolean?,

    @SerializedName("object")
    val task: Task?
)
