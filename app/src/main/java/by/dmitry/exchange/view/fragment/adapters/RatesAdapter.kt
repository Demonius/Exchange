package by.dmitry.exchange.view.fragment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import by.dmitry.exchange.base.commonui.adapters.BaseRecyclerAdapter
import by.dmitry.exchange.databinding.ItemCurrencyBinding
import by.dmitry.exchange.view.activity.model.RateCurrencyModel

class RatesAdapter(
    callback: RateListener,
    diffCallback: DiffUtil.ItemCallback<RateCurrencyModel>
) : BaseRecyclerAdapter<RateCurrencyModel, RateListener, RateViewHolder>(callback, diffCallback) {

    override fun getBindingViewHolder(viewType: Int, parent: ViewGroup): RateViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RateViewHolder(ItemCurrencyBinding.inflate(layoutInflater, parent, false))
    }
}