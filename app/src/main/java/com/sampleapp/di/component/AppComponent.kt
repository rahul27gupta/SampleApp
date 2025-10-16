package com.sampleapp.di.component

import com.sampleapp.di.module.ContextModule
import com.sampleapp.di.module.DataSourceModule
import com.sampleapp.di.module.NetworkModule
import com.sampleapp.di.module.RepositoryModule
import com.sampleapp.di.module.ViewModelModule
import com.sampleapp.feature.modules.ui.activity.ModulesActivity
import com.sampleapp.feature.quiz.ui.activity.QuizActivity
import com.sampleapp.feature.quiz.ui.activity.ResultActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ContextModule::class,
        NetworkModule::class,
        DataSourceModule::class,
        RepositoryModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {
    fun inject(activity: ModulesActivity)
    fun inject(activity: QuizActivity)
    fun inject(activity: ResultActivity)
    fun viewModelFactory(): androidx.lifecycle.ViewModelProvider.Factory
}