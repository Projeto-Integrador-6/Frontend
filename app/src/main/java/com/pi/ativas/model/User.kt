package com.pi.ativas.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: Int?,
    val name: String,
    val birthday: String?,
    val token: String?,
    val token_date: String?,
    val photo: String?,
    val registration: String?,
    val password: String?,
    val phone: String?,
    val email: String?,
    val address: String?,
    val status: Int?,
    val lattes: String?,
    val cod: String?

    ): Parcelable{
    override fun toString(): String {
        return name
    }
    }
