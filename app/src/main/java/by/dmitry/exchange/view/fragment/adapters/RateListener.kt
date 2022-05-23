package by.dmitry.exchange.view.fragment.adapters

import by.dmitry.exchange.base.commonui.adapters.BaseItemListener
import by.dmitry.exchange.view.activity.model.RateCurrencyModel

interface RateListener : BaseItemListener {

    fun changeStateFavorite(rateModel: RateCurrencyModel, isChecked: Boolean)
}