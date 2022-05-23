package by.dmitry.exchange.view.fragment.adapters

import by.dmitry.exchange.base.commonui.adapters.BaseViewHolder
import by.dmitry.exchange.databinding.ItemCurrencyBinding
import by.dmitry.exchange.view.activity.model.RateCurrencyModel

class RateViewHolder(private val viewBinding: ItemCurrencyBinding) : BaseViewHolder<RateListener, RateCurrencyModel>(viewBinding) {

    override fun bindViewModel(vm: RateCurrencyModel, position: Int) {
        with(viewBinding) {
            nameCurrency.text = vm.abbr
            rateCurrency.text = vm.rate.toString()
            checkFavorite.apply {
                isChecked = vm.isFavorite
                setOnCheckedChangeListener { _, isChecked ->
                    getCallbacks()?.changeStateFavorite(vm, isChecked)
                }
            }
        }
    }
}