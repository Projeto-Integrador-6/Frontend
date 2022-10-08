package com.pi.ativas.base

import com.google.gson.annotations.SerializedName

open class BaseBody(
    @SerializedName("email")
    open var email: String,

    @SerializedName("password")
    open var password: String,

    @SerializedName("token")
    open var token: String
)
