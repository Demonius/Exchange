package by.dmitry.exchange.view.activity.model

import by.dmitry.domain.model.CurrencyExchange
import by.dmitry.exchange.view.fragment.SortFragment

data class CurrenciesModelState(
    var listCurrencies: List<CurrencyExchange> = emptyList(),
    var favoriteCurrencies: List<CurrencyExchange> = emptyList(),
    var choosedCurrency: String = "",
    var error: String? = null,
    var sortConfiguration: SortFragment.SortConfiguration? = null,
    var isLoading: Boolean = false
)
