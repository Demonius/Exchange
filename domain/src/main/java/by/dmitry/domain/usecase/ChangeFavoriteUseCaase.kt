package by.dmitry.domain.usecase

import by.dmitry.domain.model.CurrencyExchange
import by.dmitry.domain.repositories.IDbCurrenciesRepository
import javax.inject.Inject

class ChangeFavoriteUseCaase @Inject constructor(
    private val dbService: IDbCurrenciesRepository
) : CompletableUseCase<CurrencyExchange>() {
    override suspend fun execute(request: CurrencyExchange) {
        dbService.updateCurrency(request)
    }
}