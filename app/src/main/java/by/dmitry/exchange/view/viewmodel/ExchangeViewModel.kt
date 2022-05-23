package by.dmitry.exchange.view.viewmodel

import androidx.lifecycle.viewModelScope
import by.dmitry.domain.model.CurrencyExchange
import by.dmitry.domain.model.ExchangeRates
import by.dmitry.domain.usecase.ChangeFavoriteUseCaase
import by.dmitry.domain.usecase.GetAllCurrenciesFromDbUseCase
import by.dmitry.domain.usecase.GetRatesByCurrencyUseCase
import by.dmitry.domain.usecase.UpdateAllCurrenciesUseCase
import by.dmitry.exchange.base.activity.PerActivity
import by.dmitry.exchange.base.viewmodel.BaseViewModel
import by.dmitry.exchange.view.activity.model.CurrenciesModelState
import by.dmitry.exchange.view.activity.model.RateCurrencyModel
import by.dmitry.exchange.view.activity.model.RatesModelState
import by.dmitry.exchange.view.fragment.SortFragment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@PerActivity
class ExchangeViewModel @Inject constructor(
    private val getCurrenciesUseCase: UpdateAllCurrenciesUseCase,
    private val getAllCurrenciesFromDbUseCase: GetAllCurrenciesFromDbUseCase,
    private val getRatesByCurrencyUseCase: GetRatesByCurrencyUseCase,
    private val changeFavoriteUseCase: ChangeFavoriteUseCaase
) : BaseViewModel() {

    private var sortConfiguration: SortFragment.SortConfiguration? = null

    private var curentRates: List<ExchangeRates> = emptyList()

    private val resourcesLoading = CurrenciesModelState(isLoading = true, sortConfiguration = sortConfiguration)
    private val _currencyListState = MutableStateFlow(resourcesLoading)
    val currencyListState: StateFlow<CurrenciesModelState> = _currencyListState

    private val _rates = MutableStateFlow(RatesModelState())
    val rates: StateFlow<RatesModelState> = _rates

    fun setSortConfiguration(sortConfiguration: SortFragment.SortConfiguration) {
        this.sortConfiguration = sortConfiguration
        updateState(_currencyListState, _currencyListState.value.copy(sortConfiguration = sortConfiguration))
        handleRates(curentRates)
    }

    fun getAllCurrencies() {
        viewModelScope.launch {
            getAllCurrenciesFromDbUseCase.execute(null)
                .collectWithCatch(
                    { data -> handleListCurrencies(data) },
                    { error -> handleErrorLoadCurrencies(error) })
        }
    }

    private fun handleListCurrencies(data: List<CurrencyExchange>) {
        if (data.isEmpty()) {
            viewModelScope.launch {
                getCurrenciesUseCase.execute(null)
            }
        } else {
            val favoriteList = data.filter { it.isFavorite }
            val state = CurrenciesModelState(data, favoriteList, error = null, isLoading = false, sortConfiguration = sortConfiguration)
            updateState(_currencyListState, state)
        }
    }

    private fun handleErrorLoadCurrencies(error: Throwable) {
        val state = _currencyListState.value.copy(error = error.message, isLoading = false, sortConfiguration = sortConfiguration)
        updateState(_currencyListState, state)
    }

    fun setChoosedCurrency(value: String, errorEnable: Boolean) {
        val isInclude = _currencyListState.value.listCurrencies.findLast { it.abbr == value }
        if (value.length == 3 && !errorEnable && isInclude != null) {
            val state = _currencyListState.value.copy(isLoading = true)
            updateState(_currencyListState, state)
            viewModelScope.launch {
                getRatesByCurrencyUseCase.execute(value)
                    .collectWithCatch(
                        { rates -> handleRates(rates) },
                        { error -> handleErrorLoadCurrencies(error) }
                    )
            }
        }
    }

    private fun handleRates(rates: List<ExchangeRates>) {
        updateState(_currencyListState, _currencyListState.value.copy(isLoading = false))
        curentRates = rates
        val currencies = _currencyListState.value.listCurrencies

        var ratesOut = currencies.map { currency ->
            RateCurrencyModel(currency.abbr, currency.name, rates.findLast { it.abbr == currency.abbr }?.rate ?: 0.0, currency.isFavorite)
        }

        ratesOut = when (sortConfiguration) {
            SortFragment.SortConfiguration.ALPHABET_INCRISE -> ratesOut.sortedBy { it.abbr }
            SortFragment.SortConfiguration.ALPHABET_DECRISE -> ratesOut.sortedByDescending { it.abbr }
            SortFragment.SortConfiguration.VALUE_INCRISE -> ratesOut.sortedBy { it.rate }
            SortFragment.SortConfiguration.VALUE_DECRISE -> ratesOut.sortedByDescending { it.rate }
            else -> ratesOut
        }

        val favoriteList = ratesOut.filter { it.isFavorite }


        updateState(_rates, RatesModelState(ratesOut, favoriteList))
    }

    fun changeFavoriteState(rateModel: RateCurrencyModel, checked: Boolean) {
        val currencyExchange = CurrencyExchange(rateModel.name, rateModel.abbr, checked)

        viewModelScope.launch {
            changeFavoriteUseCase.execute(currencyExchange)
        }
        _currencyListState.value.listCurrencies.findLast { it.abbr == rateModel.abbr }?.isFavorite = checked


        handleRates(curentRates)
    }
}