package com.sampleapp.feature.quiz.data

import com.sampleapp.feature.quiz.models.Question

interface QuizDataSource {
    suspend fun fetchQuestions(): ArrayList<Question>
}