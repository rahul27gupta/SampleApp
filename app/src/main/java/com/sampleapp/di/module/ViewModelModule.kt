package com.sampleapp.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sampleapp.di.ViewModelFactory
import com.sampleapp.di.ViewModelKey
import com.sampleapp.feature.modules.viewModel.ModulesViewModel
import com.sampleapp.feature.quiz.viewmodel.QuizViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(QuizViewModel::class)
    abstract fun bindQuizViewModel(viewModel: QuizViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ModulesViewModel::class)
    abstract fun bindModulesViewModel(viewModel: ModulesViewModel): ViewModel
}

