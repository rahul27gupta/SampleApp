package com.sampleapp.feature.quiz.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Question(
    @SerializedName("id")
    val id: Int,

    @SerializedName("question")
    val questionText: String,

    @SerializedName("options")
    val options: ArrayList<String>,

    @SerializedName("correctOptionIndex")
    val correctOptionIndex: Int

) : Parcelable

@Parcelize
data class QuestionState(
    val question: Question,
    val questionNumber: Int,
    val totalQuestions: Int,
    val selectedAnswerIndex: Int? = null,
    val isAnswered: Boolean = false,
    val isCorrect: Boolean? = null,
    val isSkipped: Boolean = false
) : Parcelable

