package com.sampleapp.feature.quiz.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sampleapp.database.QuizDatabase
import com.sampleapp.database.entities.QuizResultEntity
import com.sampleapp.feature.quiz.models.QuestionState
import com.sampleapp.feature.quiz.models.QuizResult
import javax.inject.Inject

class QuizResultRepositoryImpl @Inject constructor(
    private val database: QuizDatabase
) : QuizResultRepository {
    
    private val quizResultDao = database.quizResultDao()
    private val gson = Gson()
    
    override suspend fun saveQuizResult(moduleId: String, result: QuizResult) {
        val questionHistoryJson = gson.toJson(result.questionHistory)
        val entity = QuizResultEntity(
            moduleId = moduleId,
            totalQuestions = result.totalQuestions,
            correctAnswers = result.correctAnswers,
            skippedQuestions = result.skippedQuestions,
            longestStreak = result.longestStreak,
            questionHistory = questionHistoryJson
        )
        quizResultDao.insertQuizResult(entity)
    }
    
    override suspend fun getQuizResult(moduleId: String): QuizResult? {
        val entity = quizResultDao.getQuizResult(moduleId) ?: return null
        val listType = object : TypeToken<List<QuestionState>>() {}.type
        val questionHistory: List<QuestionState> = gson.fromJson(entity.questionHistory, listType)
        
        return QuizResult(
            totalQuestions = entity.totalQuestions,
            correctAnswers = entity.correctAnswers,
            skippedQuestions = entity.skippedQuestions,
            longestStreak = entity.longestStreak,
            questionHistory = questionHistory
        )
    }
    
    override suspend fun deleteQuizResult(moduleId: String) {
        quizResultDao.deleteQuizResult(moduleId)
    }
}

