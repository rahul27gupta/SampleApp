package com.sampleapp.feature.quiz.repository

import android.content.Context
import com.sampleapp.R
import com.sampleapp.feature.quiz.data.QuizDataSource
import com.sampleapp.feature.quiz.models.Question
import com.sampleapp.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(
    private val dataSource: QuizDataSource,
    private val context: Context
) : QuizRepository {

    override suspend fun getQuestions(): Resource<ArrayList<Question>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = dataSource.fetchQuestions()
                Resource.Success(response)
            } catch (e: Exception) {
                Resource.Error(null, e.message ?: context.getString(R.string.some_error))
            }
        }
    }
}

