package by.dmitry.exchange.di.modules

import by.dmitry.domain.usecase.*
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {

    @Binds
    fun bindsGetAllCurrencies(useCase: UpdateAllCurrenciesUseCase): CompletableUseCase<*>

    @Binds
    fun bindsGetDbAllCurrencies(useCase: GetAllCurrenciesFromDbUseCase): BaseUseCase<*, *>

    @Binds
    fun bindsGetRates(useCase: GetRatesByCurrencyUseCase): BaseUseCase<*, *>

    @Binds
    fun bindsUpdateFavorites(usecase: ChangeFavoriteUseCaase): CompletableUseCase<*>
}