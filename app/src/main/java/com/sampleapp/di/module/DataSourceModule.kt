package com.sampleapp.di.module

import com.sampleapp.feature.modules.data.ModuleDataSource
import com.sampleapp.feature.modules.data.ModuleDataSourceImpl
import com.sampleapp.feature.modules.repository.ModuleRepositoryImpl
import com.sampleapp.feature.quiz.data.QuizDataSource
import com.sampleapp.feature.quiz.data.QuizDataSourceImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindModuleDataSource(moduleDataSource: ModuleDataSourceImpl): ModuleDataSource

    @Binds
    @Singleton
    abstract fun bindQuizDataSource(quizDataSourceImpl: QuizDataSourceImpl): QuizDataSource
}