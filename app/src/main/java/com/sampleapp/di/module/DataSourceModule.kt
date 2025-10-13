package com.sampleapp.di.module

import com.sampleapp.feature.quiz.data.QuizDataSource
import com.sampleapp.feature.quiz.data.QuizDataSourceImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindQuizDataSource(quizDataSourceImpl: QuizDataSourceImpl): QuizDataSource
}