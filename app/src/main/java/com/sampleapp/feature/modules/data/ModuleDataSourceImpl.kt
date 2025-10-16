package com.sampleapp.feature.modules.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sampleapp.feature.modules.models.Module
import com.sampleapp.network.ApiServices
import com.sampleapp.network.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ModuleDataSourceImpl @Inject constructor(
    private val apiServices: ApiServices
) : ModuleDataSource {

    private val _modules = MutableLiveData<Resource<ArrayList<Module>?>>()
    override fun observeModules(): LiveData<Resource<ArrayList<Module>?>> = _modules
    override fun getModules() {
        _modules.postValue(Resource.Loading())
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiServices.getQuizModules()
                if (response.isSuccessful) {
                    _modules.postValue(Resource.Success(response.body()))
                } else {
                    _modules.postValue(Resource.Error(null, response.message()))
                }

            } catch (e: Exception) {
                e.printStackTrace()
                _modules.postValue(Resource.Error(null, e.message ?: "Some error"))
            }
        }
    }
}