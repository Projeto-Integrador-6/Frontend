package com.pi.ativas.base

import com.google.gson.annotations.SerializedName

open class BaseResponse(

    @SerializedName("success")
    open val success: Boolean,

    @SerializedName("message")
    open val message: String,

    @SerializedName("checkCredencials")
    open val checkCredentials: Boolean?,

    @SerializedName("generateToken")
    open val generateToken: Boolean?,

    @SerializedName("inactiveAccount")
    open val inactiveAccount: Boolean?,

    @SerializedName("changePassword")
    open val changePassword: Boolean?
)
