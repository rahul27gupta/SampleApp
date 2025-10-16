package com.sampleapp.feature.modules.repository

import com.sampleapp.database.QuizDatabase
import com.sampleapp.database.entities.ModuleProgressEntity
import com.sampleapp.feature.modules.models.Module
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ModuleProgressRepositoryImpl @Inject constructor(
    private val database: QuizDatabase
) : ModuleProgressRepository {
    
    private val moduleProgressDao = database.moduleProgressDao()
    
    override suspend fun initializeModuleProgress(module: Module) {
        val existingProgress = moduleProgressDao.getModuleProgress(module.id ?: "")
        if (existingProgress == null) {
            val moduleProgress = ModuleProgressEntity(
                moduleId = module.id ?: "",
                moduleTitle = module.title ?: "",
                totalQuestions = 10, // Fixed 10 questions per module
                correctAnswers = 0,
                isCompleted = false,
                lastAttemptDate = System.currentTimeMillis(),
                currentQuestionIndex = 0
            )
            moduleProgressDao.insertOrUpdateModuleProgress(moduleProgress)
        }
    }
    
    override suspend fun completeModule(moduleId: String, correctAnswers: Int) {
        val existingProgress = moduleProgressDao.getModuleProgress(moduleId)
        val totalQuestions = existingProgress?.totalQuestions ?: 10
        val moduleTitle = existingProgress?.moduleTitle ?: ""
        val updatedProgress = existingProgress?.copy(
            correctAnswers = correctAnswers,
            isCompleted = true,
            lastAttemptDate = System.currentTimeMillis()
        ) ?: ModuleProgressEntity(
            moduleId = moduleId,
            moduleTitle = moduleTitle,
            totalQuestions = totalQuestions,
            correctAnswers = correctAnswers,
            isCompleted = true,
            lastAttemptDate = System.currentTimeMillis(),
            currentQuestionIndex = 0
        )
        moduleProgressDao.insertOrUpdateModuleProgress(updatedProgress)
    }
    
    override suspend fun getModuleProgress(moduleId: String): ModuleProgressEntity? {
        return moduleProgressDao.getModuleProgress(moduleId)
    }
    
    override fun getAllModuleProgress(): Flow<List<ModuleProgressEntity>> {
        return moduleProgressDao.getAllModuleProgress()
    }
    
    override suspend fun resetModuleProgress(moduleId: String) {
        moduleProgressDao.deleteModuleProgress(moduleId)
    }
}
