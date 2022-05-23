package by.dmitry.data.repositories.favorites

import androidx.room.*
import by.dmitry.data.models.entity.Currencies
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesRoomService {

    @Query("SELECT * FROM currencies")
    fun getAllCurrencies(): Flow<List<Currencies>>

    @Query("SELECT * FROM currencies WHERE NOT favorite")
    fun getAllFavoriteCurrencies(): Flow<List<Currencies>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencies(currencies: List<Currencies>)

    @Query("DELETE FROM currencies")
    suspend fun clearCurrencies()

    @Query("UPDATE currencies SET favorite = :isFavorite WHERE abr =:abbr")
    suspend fun updateCurrency(isFavorite: Boolean, abbr: String)
}