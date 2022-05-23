package by.dmitry.data.models.mapper

import by.dmitry.data.models.entity.Currencies
import by.dmitry.domain.model.CurrencyExchange

class CurrencyMapper : IMapper<Currencies, CurrencyExchange> {
    override fun mapToEntity(type: CurrencyExchange) = with(type) {
        Currencies(
            name, abbr, isFavorite
        )
    }

    override fun mapFromEntity(type: Currencies) = with(type) {
        CurrencyExchange(name, abr, isFavorite)
    }
}