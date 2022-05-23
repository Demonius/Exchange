package by.dmitry.exchange.view.fragment.adapters

import androidx.recyclerview.widget.DiffUtil
import by.dmitry.exchange.view.activity.model.RateCurrencyModel

class RtesDiffUtilCallback : DiffUtil.ItemCallback<RateCurrencyModel>() {
    override fun areItemsTheSame(oldItem: RateCurrencyModel, newItem: RateCurrencyModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: RateCurrencyModel, newItem: RateCurrencyModel): Boolean {
        return oldItem.abbr == newItem.abbr
    }
}