package by.dmitry.exchange.view.activity.adapters

import android.content.Context
import android.view.View
import android.widget.Filter
import androidx.annotation.LayoutRes
import by.dmitry.domain.model.CurrencyExchange
import by.dmitry.exchange.R
import by.dmitry.exchange.base.commonui.adapters.BaseArrayAdapter
import by.dmitry.exchange.base.commonui.adapters.EmptyDropDownViewHolder

class EnterCurrenciesAdapter(
    context: Context,
    list: List<CurrencyExchange>,
    private val listener: (CurrencyExchange) -> Unit,
    private val callback: ((Boolean) -> Unit)? = null,
    @LayoutRes idRes: Int = R.layout.view_list_item
) : BaseArrayAdapter<CurrencyExchange>(context, idRes, list) {

    private val originalList = ArrayList(list)
    private val officeFilter = object : Filter() {

        private val suggestions = ArrayList<CurrencyExchange>()

        override fun convertResultToString(resultValue: Any?) =
            (resultValue as CurrencyExchange).abbr

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            if (constraint.isNullOrEmpty()) return FilterResults().apply {
                values = originalList
                count = originalList.size
            }

            suggestions.clear()
            val list =
                originalList.filter { item -> item.abbr.toLowerCase().contains(constraint.toString().toLowerCase().trim(), true) }

            suggestions.addAll(list)

            val filterResult = FilterResults()
            filterResult.apply {
                values = suggestions
                count = suggestions.size
            }

            return filterResult
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            val listItems = results?.values as List<CurrencyExchange>?

            val isEmpty = listItems.isNullOrEmpty()
            callback?.invoke(isEmpty)

            if (isEmpty) {
                clear()
                originalList.forEach { item -> add(item) }
                notifyDataSetChanged()
            } else {
                clear()
                listItems?.forEach { item -> add(item) }
                notifyDataSetChanged()
            }
        }
    }

    override fun getFilter(): Filter {
        return officeFilter
    }

    override fun createViewHolder(parent: View) = EntruCurrencyViewHolder(parent, listener)

    override fun createDropDownHolder(view: View) = EmptyDropDownViewHolder<CurrencyExchange>()
}