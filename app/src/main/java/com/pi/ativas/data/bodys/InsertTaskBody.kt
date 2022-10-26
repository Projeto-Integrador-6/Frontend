package com.pi.ativas.data.bodys

import com.google.gson.annotations.SerializedName
import java.sql.Date

data class InsertTaskBody(
    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    var password: String,

    @SerializedName("token")
    val token: String,

    @SerializedName("question_title")
    val question_title: String,

    @SerializedName("question")
    val question: String,

    @SerializedName("file")
    val file: String,

    @SerializedName("answer")
    val answer:String,

    @SerializedName("pontuation")
    val pontuation:Int,

    @SerializedName("limit_date")
    val limit_date:String,

    @SerializedName("rank")
    val rank:Int,

    @SerializedName("reduction_factor")
    val reduction_factor:Float,

    @SerializedName("grouped")
    val grouped:Int,

    @SerializedName("member_limiti")
    val member_limiti:Int,

    @SerializedName("correction")
    val correction:Int,

    @SerializedName("class_id")
    val class_id:Int
)