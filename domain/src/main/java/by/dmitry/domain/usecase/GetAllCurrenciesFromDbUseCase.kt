package by.dmitry.domain.usecase

import by.dmitry.domain.model.CurrencyExchange
import by.dmitry.domain.repositories.IDbCurrenciesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCurrenciesFromDbUseCase @Inject constructor(
    private val dbService: IDbCurrenciesRepository
) : BaseUseCase<Void?, Flow<List<CurrencyExchange>>>() {
    override suspend fun execute(request: Void?): Flow<List<CurrencyExchange>> {
        return dbService.getAllCurrencies()
    }
}