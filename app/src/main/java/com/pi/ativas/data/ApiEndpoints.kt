package com.pi.ativas.data

import com.pi.ativas.data.bodys.LoginBody
import com.pi.ativas.data.bodys.NewPasswordBody
import com.pi.ativas.data.bodys.RequestClassroomBody
import com.pi.ativas.data.bodys.TokenBody
import com.pi.ativas.data.responses.LoginResponse
import com.pi.ativas.data.responses.NewPasswordResponse
import com.pi.ativas.data.responses.RequestClassroomResponse
import com.pi.ativas.data.responses.TokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiEndpoints {

    interface TokenService{
        @Headers("Content-Type: application/json")
        @POST("generatetoken")
        suspend fun getNewToken(@Body body : TokenBody): Response<TokenResponse>
    }

    interface LoginService{
        @Headers("Content-Type: application/json")
        @POST("login")
        suspend fun getLogin(@Body body : LoginBody): Response<LoginResponse>

        @Headers("Content-Type: application/json")
        @POST("changepassword")
        suspend fun getNewPassword(@Body body : NewPasswordBody): Response<NewPasswordResponse>
    }

    interface TeacherService{
        @Headers("Content-Type: application/json")
        @POST("getmyclasses")
        suspend fun getClassroom(@Body body : RequestClassroomBody): Response<RequestClassroomResponse>
    }
}