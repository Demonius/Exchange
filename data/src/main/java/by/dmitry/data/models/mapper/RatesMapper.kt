package by.dmitry.data.models.mapper

import by.dmitry.domain.model.ExchangeRates
import com.google.gson.JsonObject

class RatesMapper : IMapper<JsonObject, List<ExchangeRates>> {
    override fun mapToEntity(type: List<ExchangeRates>) = JsonObject()

    override fun mapFromEntity(type: JsonObject): List<ExchangeRates> {
        val keys = type.keySet()

        return keys.map { abbrCurrency ->
            val valuePrimitive = type.getAsJsonPrimitive(abbrCurrency)
            ExchangeRates(abbrCurrency, valuePrimitive.asDouble)
        }
    }
}