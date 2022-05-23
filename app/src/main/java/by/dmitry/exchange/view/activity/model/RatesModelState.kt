package by.dmitry.exchange.view.activity.model

data class RatesModelState(
    val listRates: List<RateCurrencyModel> = emptyList(),
    val favoriteListRates: List<RateCurrencyModel> = emptyList()
)


data class RateCurrencyModel(
    val abbr: String,
    val name: String,
    val rate: Double,
    val isFavorite: Boolean
)