package com.sampleapp.network

import com.sampleapp.feature.quiz.models.Question
import retrofit2.http.GET

interface ApiServices {

    // Quiz API endpoint
    @GET("raw")
    suspend fun getQuizQuestions(): ArrayList<Question>
}