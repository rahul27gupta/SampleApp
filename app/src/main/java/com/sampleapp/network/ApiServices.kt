package com.sampleapp.network

import com.sampleapp.feature.modules.models.Module
import com.sampleapp.feature.quiz.models.Question
import retrofit2.Response
import retrofit2.http.GET

interface ApiServices {

    // Quiz API endpoint

    @GET(ApiConstants.MODULE)
    suspend fun getQuizModules(): Response<ArrayList<Module>>

    @GET
    suspend fun getQuizQuestions(@retrofit2.http.Url url: String): ArrayList<Question>
}