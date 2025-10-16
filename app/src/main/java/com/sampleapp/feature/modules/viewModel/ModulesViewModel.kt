package com.sampleapp.feature.modules.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sampleapp.database.entities.ModuleProgressEntity
import com.sampleapp.feature.modules.models.Module
import com.sampleapp.feature.modules.repository.ModuleProgressRepository
import com.sampleapp.feature.modules.repository.ModuleRepository
import com.sampleapp.network.Resource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class ModulesViewModel @Inject constructor(
    private val repository: ModuleRepository,
    private val moduleProgressRepository: ModuleProgressRepository
) : ViewModel() {

    private val _modulesWithProgress = MutableLiveData<Resource<List<ModuleWithProgress>>>()
    val modulesWithProgress: LiveData<Resource<List<ModuleWithProgress>>> = _modulesWithProgress

    data class ModuleWithProgress(
        val module: Module,
        val isCompleted: Boolean,
        val correctAnswers: Int,
        val totalQuestions: Int
    )

    fun loadModulesWithProgress() {
        _modulesWithProgress.value = Resource.Loading()
        repository.getModules()
        repository.observeModules().observeForever { modulesResult ->
            viewModelScope.launch {
                when (modulesResult) {
                    is Resource.Success<ArrayList<Module>?> -> {
                        val modules = modulesResult.data ?: emptyList()
                        val progressMap = getAllModuleProgress()
                        val modulesWithProgress = modules.map { module ->
                            val progress = progressMap[module.id]
                            ModuleWithProgress(
                                module = module,
                                isCompleted = progress?.isCompleted ?: false,
                                correctAnswers = progress?.correctAnswers ?: 0,
                                totalQuestions = progress?.totalQuestions ?: 10
                            )
                        }
                        _modulesWithProgress.value = Resource.Success(modulesWithProgress)
                    }

                    is Resource.Error<ArrayList<Module>?> -> {
                        _modulesWithProgress.value = Resource.Error(null, modulesResult.message)
                    }

                    is Resource.Loading<ArrayList<Module>?> -> {
                        _modulesWithProgress.value = Resource.Loading()
                    }
                }
            }
        }
    }

    private suspend fun getAllModuleProgress(): Map<String, ModuleProgressEntity> {
        val progressList = moduleProgressRepository.getAllModuleProgress().first()
        return progressList.associateBy { it.moduleId }
    }
}