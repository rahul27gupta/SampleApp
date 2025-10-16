package com.sampleapp.feature.modules.data

import androidx.lifecycle.LiveData
import com.sampleapp.feature.modules.models.Module
import com.sampleapp.network.Resource

interface ModuleDataSource {

    fun getModules()
    fun observeModules(): LiveData<Resource<ArrayList<Module>?>>
}