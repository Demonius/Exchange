package by.dmitry.exchange.base.commonui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.viewbinding.ViewBinding
import kotlinx.android.extensions.LayoutContainer


abstract class BaseArrayAdapter<T>(context: Context, @LayoutRes private val layoutId: Int, @LayoutRes private val dropDownLayoutId: Int, @Nullable objects: List<T>) :
    ArrayAdapter<T>(context, 0, 0, objects) {

    protected var inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    constructor(context: Context, @LayoutRes layoutId: Int, @Nullable objects: List<T>) : this(
        context, layoutId, 0, objects
    )

    protected abstract fun createViewHolder(parent: View): ViewHolder<T>
    protected abstract fun createDropDownHolder(parent: View): DropDownViewHolder<T>

    @NonNull
    override fun getView(position: Int, @Nullable convertView: View?, @NonNull parent: ViewGroup): View {
        var v: View? = convertView
        val h: ViewHolder<T>
        if (v == null) {
            v = createView(parent)
            h = createViewHolder(v)
            v.tag = h
        } else {
            h = v.tag as ViewHolder<T>
        }
        v.setOnClickListener { parent.performClick() }
        val item = getItem(position)
        h.hold(item)
        return v
    }

    @NonNull
    override fun getDropDownView(position: Int, @Nullable convertView: View?, @NonNull parent: ViewGroup): View {
        var v: View? = convertView
        val h: DropDownViewHolder<T>
        if (v == null) {
            v = createView(parent, dropDownLayoutId)
            h = createDropDownHolder(v)
            v.tag = h
        } else {
            h = v.tag as DropDownViewHolder<T>
        }
        val item = getItem(position)
        h.hold(item)
        return v
    }

    private fun createView(parent: ViewGroup): View {
        return inflater.inflate(layoutId, parent, false)
    }

    private fun createView(parent: ViewGroup, @LayoutRes resId: Int): View {
        return inflater.inflate(resId, parent, false)
    }

    abstract class ViewHolder<T>(
        override val containerView: View,
        private val listener: ((T) -> Unit)? = null
    ) : LayoutContainer {
        open fun hold(item: T?) {
            item?.apply {
                containerView.setOnClickListener { listener?.invoke(this) }
            }
        }
    }

    abstract class DropDownViewHolder<T>(
        override val containerView: View? = null,
        private val listener: ((T) -> Unit)? = null
    ) : LayoutContainer {
        open fun hold(item: T?) {
            item?.apply {
                containerView?.setOnClickListener { listener?.invoke(this) }
            }
        }
    }
}