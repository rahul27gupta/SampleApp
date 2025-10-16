package com.sampleapp.database.dao

import androidx.room.*
import com.sampleapp.database.entities.ModuleProgressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ModuleProgressDao {
    
    @Query("SELECT * FROM module_progress WHERE moduleId = :moduleId")
    suspend fun getModuleProgress(moduleId: String): ModuleProgressEntity?
    
    @Query("SELECT * FROM module_progress ORDER BY moduleId")
    fun getAllModuleProgress(): Flow<List<ModuleProgressEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateModuleProgress(moduleProgress: ModuleProgressEntity)
    
    @Query("UPDATE module_progress SET isCompleted = :isCompleted, correctAnswers = :correctAnswers, lastAttemptDate = :lastAttemptDate WHERE moduleId = :moduleId")
    suspend fun updateModuleCompletion(moduleId: String, isCompleted: Boolean, correctAnswers: Int, lastAttemptDate: Long)
    
    @Query("DELETE FROM module_progress WHERE moduleId = :moduleId")
    suspend fun deleteModuleProgress(moduleId: String)
}
