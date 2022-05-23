package by.dmitry.data.models.mapper

import by.dmitry.data.models.entity.Currencies
import com.google.gson.JsonObject

class CurrenciesMapper : IMapper<JsonObject, List<Currencies>> {
    override fun mapToEntity(type: List<Currencies>) = JsonObject()

    override fun mapFromEntity(type: JsonObject): List<Currencies> {
        val keys = type.keySet()

        return keys.map { abbrCurrency ->
            val valuePrimitive = type.getAsJsonPrimitive(abbrCurrency)
            Currencies(valuePrimitive.asString, abbrCurrency)
        }
    }
}