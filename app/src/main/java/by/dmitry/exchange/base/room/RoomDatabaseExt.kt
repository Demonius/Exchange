package by.dmitry.exchange.base.room

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

inline fun <T : RoomDatabase> RoomDatabase.Builder<T>.addCallback(
    crossinline onCreate: SupportSQLiteDatabase.() -> Unit = {},
    crossinline onOpen: SupportSQLiteDatabase.() -> Unit = {}
): RoomDatabase.Builder<T> = addCallback(object : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        db.onCreate()
    }

    override fun onOpen(db: SupportSQLiteDatabase) {
        super.onOpen(db)
        db.onOpen()
    }
})

inline fun <T : RoomDatabase> RoomDatabase.Builder<T>.doOnCreate(
    crossinline action: SupportSQLiteDatabase.() -> Unit
): RoomDatabase.Builder<T> = addCallback(onCreate = action)

inline fun <T : RoomDatabase> RoomDatabase.Builder<T>.doOnOpen(
    crossinline action: SupportSQLiteDatabase.() -> Unit
): RoomDatabase.Builder<T> = addCallback(onOpen = action)