package com.sampleapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sampleapp.feature.quiz.models.QuestionState

@Entity(tableName = "quiz_results")
data class QuizResultEntity(
    @PrimaryKey
    val moduleId: String,
    val totalQuestions: Int,
    val correctAnswers: Int,
    val skippedQuestions: Int,
    val longestStreak: Int,
    val questionHistory: String, // JSON string of List<QuestionState>
    val completionDate: Long = System.currentTimeMillis()
)

class QuestionHistoryConverter {
    private val gson = Gson()
    
    @TypeConverter
    fun fromQuestionHistory(value: List<QuestionState>): String {
        return gson.toJson(value)
    }
    
    @TypeConverter
    fun toQuestionHistory(value: String): List<QuestionState> {
        val listType = object : TypeToken<List<QuestionState>>() {}.type
        return gson.fromJson(value, listType)
    }
}

