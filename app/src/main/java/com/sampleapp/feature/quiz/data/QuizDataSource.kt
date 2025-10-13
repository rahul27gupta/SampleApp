package com.sampleapp.feature.quiz.data

import com.sampleapp.feature.quiz.models.Question
import com.sampleapp.network.ApiServices
import javax.inject.Inject

interface QuizDataSource {
    suspend fun fetchQuestions(): ArrayList<Question>
}

class QuizDataSourceImpl @Inject constructor(
    private val apiServices: ApiServices
) : QuizDataSource {

    override suspend fun fetchQuestions(): ArrayList<Question> = apiServices.getQuizQuestions()
}

