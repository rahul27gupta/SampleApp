package com.sampleapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "module_progress")
data class ModuleProgressEntity(
    @PrimaryKey
    val moduleId: String,
    val moduleTitle: String,
    val isCompleted: Boolean = false,
    val totalQuestions: Int = 0,
    val correctAnswers: Int = 0,
    val lastAttemptDate: Long = 0L,
    val currentQuestionIndex: Int = 0
)
