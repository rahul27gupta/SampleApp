package com.sampleapp.di.module

import com.sampleapp.feature.quiz.repository.QuizRepository
import com.sampleapp.feature.quiz.repository.QuizRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindQuizRepository(quizRepositoryImpl: QuizRepositoryImpl): QuizRepository
}