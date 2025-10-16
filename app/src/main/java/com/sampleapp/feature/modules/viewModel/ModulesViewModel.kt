package com.sampleapp.feature.modules.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sampleapp.feature.modules.models.Module
import com.sampleapp.feature.modules.repository.ModuleRepository
import com.sampleapp.network.Resource
import javax.inject.Inject

class ModulesViewModel @Inject constructor(
    private val repository: ModuleRepository,
) : ViewModel() {

    fun getModules() = repository.getModules()
    fun observeModules(): LiveData<Resource<ArrayList<Module>?>> = repository.observeModules()
}