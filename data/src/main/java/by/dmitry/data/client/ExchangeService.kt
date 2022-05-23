package by.dmitry.data.client

import by.dmitry.data.models.response.ResponseCurrencies
import by.dmitry.data.models.response.ResponseRates
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeService {

    @GET("symbols")
    suspend fun getAllCurrencies(): ResponseCurrencies

    @GET("latest")
    suspend fun getRatesByCurrency(@Query("base") currency: String): ResponseRates

}