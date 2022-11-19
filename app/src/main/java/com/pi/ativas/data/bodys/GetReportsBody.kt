package com.pi.ativas.data.bodys

import com.google.gson.annotations.SerializedName

data class GetReportsBody(
    @SerializedName("email")
    var email: String,

    @SerializedName("password")
    var password: String,

    @SerializedName("token")
    var token: String,

    @SerializedName("task_id")
    var task_id :Int,

    @SerializedName("type")
    var type: Int,


)
