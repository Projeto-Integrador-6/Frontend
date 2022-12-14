package com.pi.ativas.data.responses

import com.google.gson.annotations.SerializedName
import com.pi.ativas.model.RankingStudent

data class RequestRankingResponse(
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
    val content: List<RankingStudent>?,

    @SerializedName("numberOfTasks")
    val numberOfTasks: Int?,

    @SerializedName("numberOfStudents")
    val numberOfStudents: Int?,

    @SerializedName("studentPosition")
    val studentPosition: Int?
)
