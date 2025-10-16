package com.sampleapp.feature.modules.repository

import androidx.lifecycle.LiveData
import com.sampleapp.feature.modules.data.ModuleDataSource
import com.sampleapp.feature.modules.models.Module
import com.sampleapp.network.Resource
import javax.inject.Inject

class ModuleRepositoryImpl @Inject constructor(
    private val dataSource: ModuleDataSource
) : ModuleRepository {

    override fun getModules() = dataSource.getModules()
    override fun observeModules(): LiveData<Resource<ArrayList<Module>?>> =
        dataSource.observeModules()
}