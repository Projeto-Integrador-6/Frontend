package com.pi.ativas.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateUtils {

    private val date: LocalDateTime = LocalDateTime.now()
    private val dateAPI = DateTimeFormatter.ofPattern("yyyy-MM-dd HH.mm.ss")
    private val hours = date.format(dateAPI)

    fun getDate(): String {
        return hours
    }
}