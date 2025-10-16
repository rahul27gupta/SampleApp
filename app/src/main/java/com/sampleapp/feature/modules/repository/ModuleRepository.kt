package com.sampleapp.feature.modules.repository

import androidx.lifecycle.LiveData
import com.sampleapp.feature.modules.models.Module
import com.sampleapp.network.Resource

interface ModuleRepository {
    fun getModules()
    fun observeModules(): LiveData<Resource<ArrayList<Module>?>>
}