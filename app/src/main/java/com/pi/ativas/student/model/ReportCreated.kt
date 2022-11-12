package com.pi.ativas.student.model

data class ReportCreated(
    val data: String,
    val answer: String,
    val task_id: String,
    val anexo: String?,
    val team_id: String,
    val id: String
)
