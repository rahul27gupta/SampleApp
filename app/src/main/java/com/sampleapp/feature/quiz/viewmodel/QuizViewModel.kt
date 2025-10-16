package com.sampleapp.feature.quiz.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sampleapp.R
import com.sampleapp.feature.quiz.models.*
import com.sampleapp.feature.quiz.repository.QuizRepository
import com.sampleapp.network.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class QuizViewModel @Inject constructor(
    private val repository: QuizRepository,
    private val quizResultRepository: com.sampleapp.feature.quiz.repository.QuizResultRepository,
    private val context: Context
) : ViewModel() {

    private val _uiState = MutableLiveData<QuizUiState>(QuizUiState.Loading)
    val uiState: LiveData<QuizUiState> = _uiState

    private var questions: List<Question> = emptyList()
    private var currentQuestionIndex = 0
    private var correctAnswersCount = 0
    private var skippedQuestionsCount = 0
    private var currentStreak = 0
    private var longestStreak = 0
    private val questionHistory = mutableListOf<QuestionState>()
    private var isReviewMode = false
    private var savedQuestionHistory: List<QuestionState> = emptyList()

    fun loadQuestions(questionsUrl: String) {
        isReviewMode = false
        viewModelScope.launch {
            _uiState.value = QuizUiState.Loading
            when (val result = repository.getQuestions(questionsUrl)) {
                is Resource.Success -> {
                    questions = result.data ?: emptyList()
                    if (questions.isNotEmpty()) {
                        showNextQuestion()
                    } else {
                        _uiState.value =
                            QuizUiState.Error(context.getString(R.string.no_questions_available))
                    }
                }

                is Resource.Error -> {
                    _uiState.value =
                        QuizUiState.Error(result.message ?: context.getString(R.string.some_error))
                }

                is Resource.Loading -> {
                    // Already showing loading
                }
            }
        }
    }

    fun loadQuestionsForReview(questionsUrl: String, moduleId: String) {
        isReviewMode = true
        viewModelScope.launch {
            _uiState.value = QuizUiState.Loading
            when (val result = repository.getQuestions(questionsUrl)) {
                is Resource.Success -> {
                    questions = result.data ?: emptyList()

                    val savedResult = quizResultRepository.getQuizResult(moduleId)
                    if (savedResult != null && questions.isNotEmpty()) {
                        savedQuestionHistory = savedResult.questionHistory
                        correctAnswersCount = savedResult.correctAnswers
                        longestStreak = savedResult.longestStreak
                        showNextQuestion()
                    } else {
                        _uiState.value =
                            QuizUiState.Error(context.getString(R.string.no_questions_available))
                    }
                }

                is Resource.Error -> {
                    _uiState.value =
                        QuizUiState.Error(result.message ?: context.getString(R.string.some_error))
                }

                is Resource.Loading -> {
                    // Already showing loading
                }
            }
        }
    }

    fun selectAnswer(answerIndex: Int) {
        val currentState = _uiState.value
        if (currentState is QuizUiState.QuizInProgress) {
            val question = currentState.currentQuestion
            if (question.isAnswered) return
            val isCorrect = answerIndex == question.question.correctOptionIndex
            if (isCorrect) {
                correctAnswersCount++
                currentStreak++
                if (currentStreak > longestStreak) {
                    longestStreak = currentStreak
                }
            } else {
                currentStreak = 0
            }

            val updatedQuestion = question.copy(
                selectedAnswerIndex = answerIndex,
                isAnswered = true,
                isCorrect = isCorrect
            )

            questionHistory.add(updatedQuestion)

            _uiState.value = currentState.copy(
                currentQuestion = updatedQuestion,
                currentStreak = currentStreak,
                longestStreak = longestStreak,
                correctAnswers = correctAnswersCount,
                isStreakBadgeActive = currentStreak >= 3
            )

            if (!isReviewMode) {
                viewModelScope.launch {
                    delay(2000)
                    moveToNextQuestion()
                }
            }
        }
    }

    fun skipQuestion() {
        val currentState = _uiState.value
        if (currentState is QuizUiState.QuizInProgress) {
            val question = currentState.currentQuestion
            if (question.isAnswered) return
            skippedQuestionsCount++
            currentStreak = 0
            val skippedQuestion = question.copy(
                isSkipped = true,
                isAnswered = true
            )
            questionHistory.add(skippedQuestion)
            moveToNextQuestion()
        }
    }

    private fun moveToNextQuestion() {
        currentQuestionIndex++
        if (currentQuestionIndex < questions.size) {
            showNextQuestion()
        } else {
            showResults()
        }
    }

    private fun showNextQuestion() {
        val question = questions[currentQuestionIndex]
        val questionState = if (isReviewMode && currentQuestionIndex < savedQuestionHistory.size) {
            val savedQuestion = savedQuestionHistory[currentQuestionIndex]
            QuestionState(
                question = question,
                questionNumber = currentQuestionIndex + 1,
                totalQuestions = questions.size,
                selectedAnswerIndex = savedQuestion.selectedAnswerIndex,
                isAnswered = true,
                isCorrect = savedQuestion.isCorrect,
                isSkipped = savedQuestion.isSkipped
            )
        } else {
            QuestionState(
                question = question,
                questionNumber = currentQuestionIndex + 1,
                totalQuestions = questions.size
            )
        }

        _uiState.value = QuizUiState.QuizInProgress(
            currentQuestion = questionState,
            currentStreak = currentStreak,
            longestStreak = longestStreak,
            correctAnswers = correctAnswersCount,
            skippedQuestions = skippedQuestionsCount,
            isStreakBadgeActive = currentStreak >= 3
        )
    }

    fun nextQuestion() {
        if (isReviewMode) {
            moveToNextQuestion()
        }
    }

    private fun showResults() {
        val result = QuizResult(
            totalQuestions = questions.size,
            correctAnswers = correctAnswersCount,
            skippedQuestions = skippedQuestionsCount,
            longestStreak = longestStreak,
            questionHistory = questionHistory
        )
        _uiState.value = QuizUiState.QuizCompleted(result)
    }
}

