package com.pi.ativas.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Task(
    val id: Int,
    val question: String,
    val file: String,
    val answer: String,
    val pontuation: Int,
    val limit_date: String,
    val delivery_date: String?,
    val question_title: String,
    val rank: Int,
    val reduction_factor: Float,
    val grouped: Int,
    val member_limiti: Int,
    val correction: Int,
    val class_id: Int
) : Parcelable
