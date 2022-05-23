package by.dmitry.exchange.di.modules

import android.content.Context
import by.dmitry.data.client.ExchangeService
import by.dmitry.data.client.RemoteClient
import by.dmitry.data.db.AppDatabase
import by.dmitry.data.repositories.favorites.FavoritesRoomService
import by.dmitry.exchange.di.scope.PerApplication
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
object DataModule {

    @Provides
    @PerApplication
    fun providesRetrofit(appContext: Context): Retrofit = RemoteClient.makeNetworkClient(appContext)


    @Provides
    fun providesExchangeService(retrofit: Retrofit): ExchangeService =
        retrofit.create(ExchangeService::class.java)

    @Provides
    fun providesDiscountCardService(appDatabase: AppDatabase): FavoritesRoomService =
        appDatabase.favoriteService()

}