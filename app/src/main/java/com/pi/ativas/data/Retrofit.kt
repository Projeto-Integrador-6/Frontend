package com.pi.ativas.data

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import com.pi.ativas.data.ApiEndpoints.*

object Retrofit {

    private const val TEACHER_BASE_URL = "http://ativas-env.eba-nxnrtidm.sa-east-1.elasticbeanstalk.com/api/teacher/"
    private const val STUDENT_BASE_URL = "http://ativas-env.eba-nxnrtidm.sa-east-1.elasticbeanstalk.com/api/student/"

    private fun getRetrofit(baseURL: String): Retrofit {
        val gsonBuilder = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
            .build()
    }

    //STUDENT FUNCTIONS
    val tokenApiStudent: TokenService = getRetrofit(STUDENT_BASE_URL).create(TokenService::class.java)
    val loginApiStudent: LoginService = getRetrofit(STUDENT_BASE_URL).create(LoginService::class.java)

    //TEACHER FUNCTION
    val tokenApiTeacher: TokenService = getRetrofit(TEACHER_BASE_URL).create(TokenService::class.java)
    val loginApiTeacher: LoginService = getRetrofit(TEACHER_BASE_URL).create(LoginService::class.java)
}