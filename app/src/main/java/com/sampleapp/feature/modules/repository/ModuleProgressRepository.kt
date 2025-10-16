package com.sampleapp.feature.modules.repository

import com.sampleapp.database.entities.ModuleProgressEntity
import com.sampleapp.feature.modules.models.Module
import kotlinx.coroutines.flow.Flow

interface ModuleProgressRepository {
    suspend fun initializeModuleProgress(module: Module)
    suspend fun completeModule(moduleId: String, correctAnswers: Int)
    suspend fun getModuleProgress(moduleId: String): ModuleProgressEntity?
    fun getAllModuleProgress(): Flow<List<ModuleProgressEntity>>
    suspend fun resetModuleProgress(moduleId: String)
}
