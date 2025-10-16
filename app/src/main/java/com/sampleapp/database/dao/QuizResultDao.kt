package com.sampleapp.database.dao

import androidx.room.*
import com.sampleapp.database.entities.QuizResultEntity

@Dao
interface QuizResultDao {
    
    @Query("SELECT * FROM quiz_results WHERE moduleId = :moduleId")
    suspend fun getQuizResult(moduleId: String): QuizResultEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizResult(quizResult: QuizResultEntity)
    
    @Query("DELETE FROM quiz_results WHERE moduleId = :moduleId")
    suspend fun deleteQuizResult(moduleId: String)
}

