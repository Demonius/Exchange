package by.dmitry.exchange.base.commonui.adapters

import androidx.recyclerview.widget.DiffUtil

class DiffCallback<T>(
    private val oldData: List<T>,
    private val newData: List<T>,
    private val itemCallback: DiffUtil.ItemCallback<T>,
    private val changePayload: ((oldData: T, newData: T) -> Any?)?
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldData.size
    override fun getNewListSize(): Int = newData.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        itemCallback.areContentsTheSame(oldData[oldItemPosition], newData[newItemPosition])

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        itemCallback.areItemsTheSame(oldData[oldItemPosition], newData[newItemPosition])

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? =
        changePayload?.invoke(oldData[oldItemPosition], newData[newItemPosition])
}

inline fun <T> diffCallback(
    crossinline areItemsTheSame: (old: T, new: T) -> Boolean,
    crossinline areContentsTheSame: (old: T, new: T) -> Boolean = { old, new -> old == new }
): DiffUtil.ItemCallback<T> = object : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
        areItemsTheSame(oldItem, newItem)

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
        areContentsTheSame(oldItem, newItem)
}