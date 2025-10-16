package com.sampleapp.di.module

import com.sampleapp.database.QuizDatabase
import com.sampleapp.feature.modules.data.ModuleDataSource
import com.sampleapp.feature.modules.data.ModuleDataSourceImpl
import com.sampleapp.feature.modules.repository.ModuleProgressRepository
import com.sampleapp.feature.modules.repository.ModuleProgressRepositoryImpl
import com.sampleapp.feature.quiz.data.QuizDataSource
import com.sampleapp.feature.quiz.data.QuizDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindModuleDataSource(moduleDataSource: ModuleDataSourceImpl): ModuleDataSource

    @Binds
    @Singleton
    abstract fun bindQuizDataSource(quizDataSourceImpl: QuizDataSourceImpl): QuizDataSource

    @Binds
    @Singleton
    abstract fun bindModuleProgressRepository(repository: ModuleProgressRepositoryImpl): ModuleProgressRepository

    companion object {
        @Provides
        @Singleton
        fun provideQuizDatabase(context: android.content.Context): QuizDatabase {
            return QuizDatabase.getDatabase(context)
        }
    }
}