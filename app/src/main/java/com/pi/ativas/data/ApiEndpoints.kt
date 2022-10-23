package com.pi.ativas.data

import com.pi.ativas.data.bodys.*
import com.pi.ativas.data.responses.*
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

        @Headers("Content-Type: application/json")
        @POST("getmyclasstasks")
        suspend fun getClassroomTasks(@Body body : RequestTaskBody): Response<RequestTaskResponse>

        @Headers("Content-Type: application/json")
        @POST("getTeamsinTask")
        suspend fun getTaskTeams(@Body body : RequestTaskTeamsBody): Response<RequestTaskTeamsResponse>
    }

    interface StudentService{
        @Headers("Content-Type: application/json")
        @POST("getmytasks")
        suspend fun getTasks(@Body body : RequestTaskBody): Response<RequestTaskResponse>
    }
}