package com.pi.ativas.data.responses

import com.google.gson.annotations.SerializedName
import com.pi.ativas.teacher.model.Team

data class RequestTaskTeamsResponse(
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

    @SerializedName("changePassword")
    val changePassword: Boolean?,

    @SerializedName("content")
    val content: ArrayList<Team>?,

    @SerializedName("total")
    val total: Int?
)