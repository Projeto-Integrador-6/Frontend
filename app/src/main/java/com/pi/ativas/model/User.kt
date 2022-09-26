package com.pi.ativas.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class User(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("birthday")
    val birthday: String?,

    @SerializedName("token")
    val token: String?,

    @SerializedName("token_date")
    val token_date: String?,

    @SerializedName("photo")
    val photo: String?,

    @SerializedName("registration")
    val registration: String?,

    @SerializedName("password")
    val password: String?,

    @SerializedName("telephone")
    val telephone: String?,

    @SerializedName("email")
    val email: String?,

    @SerializedName("address")
    val address: String?,

    @SerializedName("status")
    val status: Int?,

    @SerializedName("cod")
    val cod: String?
)
