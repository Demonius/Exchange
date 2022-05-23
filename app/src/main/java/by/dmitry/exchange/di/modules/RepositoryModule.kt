package by.dmitry.exchange.di.modules

import by.dmitry.data.repositories.exchange.DbCurrenciesRepository
import by.dmitry.data.repositories.exchange.ExchangeRepository
import by.dmitry.domain.repositories.IDbCurrenciesRepository
import by.dmitry.domain.repositories.IExchangeRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun bindExchangeRepository(repository: ExchangeRepository): IExchangeRepository

    @Binds
    fun bindDbCurrencyRepository(repository: DbCurrenciesRepository): IDbCurrenciesRepository

}