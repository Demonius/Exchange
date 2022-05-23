package by.dmitry.exchange.view.activity.adapters

import android.view.View
import by.dmitry.domain.model.CurrencyExchange
import by.dmitry.exchange.R
import by.dmitry.exchange.base.commonui.adapters.BaseArrayAdapter
import com.google.android.material.textview.MaterialTextView

class EntruCurrencyViewHolder(private val view: View, listener: (CurrencyExchange) -> Unit) :
    BaseArrayAdapter.ViewHolder<CurrencyExchange>(view, listener) {
    override fun hold(item: CurrencyExchange?) {
        super.hold(item)

        item?.run {
            view.findViewById<MaterialTextView>(R.id.tvText).text = StringBuilder().append(abbr).append(" ").append(name).toString()
        }
    }
}