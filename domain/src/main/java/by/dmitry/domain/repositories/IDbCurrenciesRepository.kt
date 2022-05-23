package by.dmitry.domain.repositories

import by.dmitry.domain.model.CurrencyExchange
import kotlinx.coroutines.flow.Flow

interface IDbCurrenciesRepository {

    fun getAllCurrencies(): Flow<List<CurrencyExchange>>

    fun getFavoriteCurrencies(): Flow<List<CurrencyExchange>>

    suspend fun updateCurrency(currency: CurrencyExchange)
}