package by.dmitry.domain.repositories

import by.dmitry.domain.model.ExchangeRates
import kotlinx.coroutines.flow.Flow

interface IExchangeRepository {

   suspend fun getAllCurrencies()

   suspend fun getExchangRatesByCurrency(currency:String): Flow<List<ExchangeRates>>
}