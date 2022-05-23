package by.dmitry.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import by.dmitry.data.models.entity.Currencies
import by.dmitry.data.repositories.favorites.FavoritesRoomService

@Database(
    entities = [
        Currencies::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteService(): FavoritesRoomService
}