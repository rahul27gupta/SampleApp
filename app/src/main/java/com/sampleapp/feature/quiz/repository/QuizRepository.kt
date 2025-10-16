package com.sampleapp.feature.quiz.repository

import com.sampleapp.feature.quiz.models.Question
import com.sampleapp.network.Resource

interface QuizRepository {
    suspend fun getQuestions(): Resource<ArrayList<Question>>
}