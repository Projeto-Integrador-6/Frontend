package com.pi.ativas.data.bodys

import com.google.gson.annotations.SerializedName

data class UpsetReportsBody(
    @SerializedName("email")
    var email: String,

    @SerializedName("password")
    var password: String,

    @SerializedName("token")
    var token: String,

    @SerializedName("pontuation")
    var pontuation :Int,

    @SerializedName("reportId")
    var reportId: Int,


)
