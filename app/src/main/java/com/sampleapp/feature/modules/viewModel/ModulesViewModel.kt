package com.sampleapp.feature.modules.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sampleapp.database.entities.ModuleProgressEntity
import com.sampleapp.feature.modules.models.Module
import com.sampleapp.feature.modules.repository.ModuleProgressRepository
import com.sampleapp.feature.modules.repository.ModuleRepository
import com.sampleapp.network.Resource
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ModulesViewModel @Inject constructor(
    private val repository: ModuleRepository,
    private val moduleProgressRepository: ModuleProgressRepository
) : ViewModel() {

    data class ModuleWithProgress(
        val module: Module,
        val isCompleted: Boolean,
        val correctAnswers: Int,
        val totalQuestions: Int
    )

    fun getModules() = repository.getModules()
    fun observeModules(): LiveData<Resource<ArrayList<Module>?>> = repository.observeModules()

    suspend fun combineModulesWithProgress(modules: List<Module>): List<ModuleWithProgress> {
        val progressMap = getAllModuleProgress()
        return modules.map { module ->
            val progress = progressMap[module.id]
            ModuleWithProgress(
                module = module,
                isCompleted = progress?.isCompleted ?: false,
                correctAnswers = progress?.correctAnswers ?: 0,
                totalQuestions = progress?.totalQuestions ?: 10
            )
        }
    }

    private suspend fun getAllModuleProgress(): Map<String, ModuleProgressEntity> {
        val progressList = moduleProgressRepository.getAllModuleProgress().first()
        return progressList.associateBy { it.moduleId }
    }
}