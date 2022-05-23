package by.dmitry.exchange.di.modules

import androidx.lifecycle.ViewModelProvider
import by.dmitry.exchange.base.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}