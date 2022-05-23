package by.dmitry.data.repositories.exchange

import by.dmitry.data.client.ExchangeService
import by.dmitry.data.models.mapper.CurrenciesMapper
import by.dmitry.data.models.mapper.RatesMapper
import by.dmitry.data.repositories.favorites.FavoritesRoomService
import by.dmitry.domain.model.ExchangeRates
import by.dmitry.domain.repositories.IExchangeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ExchangeRepository @Inject constructor(
    private val dbService: FavoritesRoomService,
    private val service: ExchangeService
) : IExchangeRepository {
    override suspend fun getAllCurrencies() {
        val response = service.getAllCurrencies()
        val currenciesJsonObject = response.symbols
        val listCurrencies = CurrenciesMapper().mapFromEntity(currenciesJsonObject)
        dbService.insertCurrencies(listCurrencies)
    }

    override suspend fun getExchangRatesByCurrency(currency: String): Flow<List<ExchangeRates>> {
        val response = service.getRatesByCurrency(currency)
        return flow {
            emit(RatesMapper().mapFromEntity(response.rates))
        }

    }
}