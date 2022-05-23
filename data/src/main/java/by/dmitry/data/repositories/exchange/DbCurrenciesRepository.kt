package by.dmitry.data.repositories.exchange

import by.dmitry.data.models.mapper.CurrencyMapper
import by.dmitry.data.repositories.favorites.FavoritesRoomService
import by.dmitry.domain.model.CurrencyExchange
import by.dmitry.domain.repositories.IDbCurrenciesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DbCurrenciesRepository @Inject constructor(
    private val dbService: FavoritesRoomService
) : IDbCurrenciesRepository {
    override fun getAllCurrencies(): Flow<List<CurrencyExchange>> {
        val mapper = CurrencyMapper()
        return dbService.getAllCurrencies().map { list -> list.map { mapper.mapFromEntity(it) } }
    }

    override fun getFavoriteCurrencies(): Flow<List<CurrencyExchange>> {
        val mapper = CurrencyMapper()
        return dbService.getAllFavoriteCurrencies().map { list -> list.map { mapper.mapFromEntity(it) } }
    }

    override suspend fun updateCurrency(currency: CurrencyExchange) {
        dbService.updateCurrency(currency.isFavorite, currency.abbr)
    }
}