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
        @POST("insertTask")
        suspend fun getInsertTask(@Body body : InsertTaskBody): Response<InsertTaskResponse>

        @Headers("Content-Type: application/json")
        @POST("createteam")
        suspend fun getCreateTeam(@Body body : CreateTeamBody): Response<CreateTeamResponse>

        @Headers("Content-Type: application/json")
        @POST("getstudentsinclass")
        suspend fun getStudentsInClass(@Body body : StudentsInClassBody): Response<StudentsInClassResponse>

        @Headers("Content-Type: application/json")
        @POST("getTeamsinTask")
        suspend fun getTaskTeams(@Body body : RequestTaskTeamsBody): Response<RequestTaskTeamsResponse>

        @Headers("Content-Type: application/json")
        @POST("ranking")
        suspend fun requestRanking(@Body body : RequestRankingBody): Response<RequestRankingResponse>
    }

    interface StudentService{
        @Headers("Content-Type: application/json")
        @POST("getmytasks")
        suspend fun getTasks(@Body body : RequestTaskBody): Response<RequestTaskResponse>

        @Headers("Content-Type: application/json")
        @POST("postreport")
        suspend fun postReport(@Body body : ReportBody): Response<ReportResponse>

        @Headers("Content-Type: application/json")
        @POST("ranking")
        suspend fun requestRanking(@Body body : RequestRankingBody): Response<RequestRankingResponse>

        @Headers("Content-Type: application/json")
        @POST("getmyteachers")
        suspend fun getTeachers(@Body body : GetTeachersBody): Response<GetTeachersResponse>

        @Headers("Content-Type: application/json")
        @POST("getmydisciplines")
        suspend fun getDisciplines(@Body body : LoginBody): Response<GetDisciplinesResponse>
    }
}