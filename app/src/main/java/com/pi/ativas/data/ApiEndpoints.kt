package com.pi.ativas.data

import com.pi.ativas.data.bodys.LoginBody
import com.pi.ativas.data.bodys.TokenBody
import com.pi.ativas.data.responses.LoginResponse
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
    }
}