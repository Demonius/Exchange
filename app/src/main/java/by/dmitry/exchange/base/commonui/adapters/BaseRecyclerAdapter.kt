package by.dmitry.exchange.base.commonui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class BaseRecyclerAdapter<VM : Any, C : BaseItemListener, VH : BaseViewHolder<*, VM>>(
    private val callback: C, diffCallback: DiffUtil.ItemCallback<VM>
) : ListAdapter<VM, VH>(diffCallback) {

    protected abstract fun getBindingViewHolder(viewType: Int, parent: ViewGroup): VH

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        getBindingViewHolder(viewType, parent)

    override fun onBindViewHolder(holder: VH, position: Int, payloads: MutableList<Any>) = when {
        payloads.isEmpty() -> onBindViewHolder(holder, position)
        else -> proceedPayloads(holder, position, payloads)
    }

    open fun proceedPayloads(holder: VH, position: Int, payloads: MutableList<Any>) {}

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.setCallbacks(callback)

        val viewModel = getItem(position)
        holder.bindViewModel(viewModel, position)
    }

    override fun getItemId(position: Int) = currentList[position]
        .hashCode()
        .toLong()
}