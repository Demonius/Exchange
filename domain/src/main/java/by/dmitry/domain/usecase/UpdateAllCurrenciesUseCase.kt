package by.dmitry.domain.usecase

import by.dmitry.domain.repositories.IExchangeRepository
import javax.inject.Inject

class UpdateAllCurrenciesUseCase @Inject constructor(
    private val exchangeRepository: IExchangeRepository
) : CompletableUseCase<Void?>() {
    override suspend fun execute(request: Void?) {
        exchangeRepository.getAllCurrencies()
    }
}