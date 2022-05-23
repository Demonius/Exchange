package by.dmitry.domain.usecase

import by.dmitry.domain.model.ExchangeRates
import by.dmitry.domain.repositories.IExchangeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRatesByCurrencyUseCase @Inject constructor(
    private val exchangeRepository: IExchangeRepository
) : BaseUseCase<String, Flow<List<ExchangeRates>>>() {
    override suspend fun execute(request: String): Flow<List<ExchangeRates>> {
        return exchangeRepository.getExchangRatesByCurrency(request)
    }
}