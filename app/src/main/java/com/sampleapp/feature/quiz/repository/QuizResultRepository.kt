package com.sampleapp.feature.quiz.repository

import com.sampleapp.feature.quiz.models.QuizResult

interface QuizResultRepository {
    suspend fun saveQuizResult(moduleId: String, result: QuizResult)
    suspend fun getQuizResult(moduleId: String): QuizResult?
    suspend fun deleteQuizResult(moduleId: String)
}

