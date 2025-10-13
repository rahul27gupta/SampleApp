package com.sampleapp.feature.quiz.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class QuizUiState {
    data object Loading : QuizUiState()
    data class QuizInProgress(
        val currentQuestion: QuestionState,
        val currentStreak: Int = 0,
        val longestStreak: Int = 0,
        val correctAnswers: Int = 0,
        val skippedQuestions: Int = 0,
        val isStreakBadgeActive: Boolean = false
    ) : QuizUiState()

    data class QuizCompleted(
        val result: QuizResult
    ) : QuizUiState()

    data class Error(val message: String) : QuizUiState()
}

@Parcelize
data class QuizResult(
    val totalQuestions: Int,
    val correctAnswers: Int,
    val skippedQuestions: Int,
    val longestStreak: Int,
    val questionHistory: List<QuestionState>
) : Parcelable {
    val percentage: Int
        get() = if (totalQuestions > 0) (correctAnswers * 100) / totalQuestions else 0

    val score: String
        get() = "$correctAnswers/$totalQuestions"
}

