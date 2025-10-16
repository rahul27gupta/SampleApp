package com.sampleapp.feature.quiz.data

import com.sampleapp.feature.quiz.models.Question
import com.sampleapp.network.ApiConstants
import com.sampleapp.network.ApiServices
import javax.inject.Inject

class QuizDataSourceImpl @Inject constructor(
    private val apiServices: ApiServices
) : QuizDataSource {

    override suspend fun fetchQuestions(questionsUrl: String): ArrayList<Question> {
        return apiServices.getQuizQuestions(questionsUrl)
    }
}

