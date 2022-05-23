package by.dmitry.exchange.base.commonui.adapters


import android.content.Context
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

open class BaseViewHolder<I : BaseItemListener, VM : Any>(private val itemBinding: ViewBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    protected fun provideContext(): Context = itemView.context

    protected fun getString(@StringRes stringRes: Int): String =
        provideContext().getString(stringRes)

    fun getString(@StringRes resId: Int, vararg formatArgs: Any?): String {
        return provideContext().getString(resId, *formatArgs)
    }

    private var callbacks: I? = null

    open fun bindViewModel(vm: VM, position: Int) {

    }

    open fun getCallbacks(): I? {
        return callbacks
    }

    open fun <C> setCallbacks(callbacks: C) {
        this.callbacks = callbacks as I
    }
}