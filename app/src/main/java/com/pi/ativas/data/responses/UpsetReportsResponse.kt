package com.pi.ativas.data.responses

import com.google.gson.annotations.SerializedName
import com.pi.ativas.model.Classroom
import com.pi.ativas.model.Report
import com.pi.ativas.model.User
import com.pi.ativas.student.model.ReportCreated
import com.pi.ativas.teacher.model.PontuationReport

data class UpsetReportsResponse(
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
    val pontuationReport: PontuationReport?,

    @SerializedName("total")
    val total: Int?
)
