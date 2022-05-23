package by.dmitry.exchange.base.commonui.edittext

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import androidx.core.content.withStyledAttributes
import by.dmitry.exchange.R
import com.google.android.material.textfield.MaterialAutoCompleteTextView

class AutocompleteInputView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseInputView<MaterialAutoCompleteTextView>(context, attrs, defStyleAttr) {

    var threshold: Int
        set(value) {
            textView.threshold = value
        }
        get() {
            return textView.threshold
        }

    init {
        isSaveEnabled = true
        orientation = VERTICAL
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)

        inflate(context, R.layout.view_autocomplete, this)

        initViews()
        initAttrs(attrs)
    }

    private fun initViews() {
        title = findViewById(R.id.select_tv_title)
        title.apply {
            isLabelVisible = true
            isTextVisible = false
        }
        inputLayout = findViewById(R.id.select_til_text)
        textView = findViewById(R.id.select_edt_text)
    }

    private fun initAttrs(attrs: AttributeSet? = null) {
        context.withStyledAttributes(attrs, R.styleable.TextInputView) {
            getString(R.styleable.TextInputView_title)?.let { titleText = it }

            getBoolean(R.styleable.TextInputView_errorEnabled, false).let { isErrorEnable = it }
            getString(R.styleable.TextInputView_errorText)?.let { errorText = it }

            getString(R.styleable.TextInputView_prefixText)?.let { prefix = it }
            getString(R.styleable.TextInputView_suffixText)?.let { suffix = it }

            setImeOptions(
                getInt(R.styleable.TextInputView_android_imeOptions, EditorInfo.IME_ACTION_DONE)
            )
            setImeActionId(getInt(R.styleable.TextInputView_android_imeActionId, NULL_ACTION_ID))
        }
    }

    fun setAdapter(adapter: ArrayAdapter<*>) {
        textView.setAdapter(adapter)
    }

    fun setItemsList(list: List<String>) {
        if (list.isEmpty()) return
        val adapter = ArrayAdapter(context, R.layout.view_list_item, list)
        textView.setAdapter(adapter)
    }

    fun addOnTextChangeListener(textWatcher: TextWatcher) {
        textView.addTextChangedListener(textWatcher)
    }

    fun addOnClickListener(clickListener: OnClickListener) {
        textView.setOnClickListener(clickListener)
    }

    fun closeExpandedList() {
        textView.dismissDropDown()
    }

    fun removeTextWatcher(textWatcher: TextWatcher) {
        textView.removeTextChangedListener(textWatcher)
    }

    fun addFocusChangeListener(listener: View.OnFocusChangeListener) {
        textView.onFocusChangeListener = listener
    }

    fun setMaxLengthInput(maxLength: Int) {
        val filters = ArrayList(textView.filters.toList())
        val maxLengthFilter = InputFilter.LengthFilter(maxLength)
        filters.add(maxLengthFilter)
        textView.filters = filters.toTypedArray()
    }

    override fun onSaveInstanceState(): Parcelable =
        SavedState(super.onSaveInstanceState()).apply {
            this.labelText = this@AutocompleteInputView.titleText
            this.isErrorEnable = this@AutocompleteInputView.isErrorEnable
            this.errorText = this@AutocompleteInputView.errorText
            this.prefix = this@AutocompleteInputView.prefix
            this.suffix = this@AutocompleteInputView.suffix
        }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val savedState = state as SavedState
        super.onRestoreInstanceState(savedState.superState)

        titleText = savedState.labelText.toString()

        isErrorEnable = savedState.isErrorEnable
        errorText = savedState.errorText

        prefix = savedState.prefix
        suffix = savedState.suffix
    }

    fun showExpandedList() {
        textView.showDropDown()
    }

    internal class SavedState : BaseSavedState {

        @Suppress("unused")
        companion object {
            @JvmField
            val CREATOR = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(source: Parcel): SavedState = SavedState(source)
                override fun newArray(size: Int): Array<SavedState?> = arrayOfNulls(size)
            }
        }

        var labelText: CharSequence? = null
        var isErrorEnable: Boolean = false
        var errorText: CharSequence? = null
        var prefix: CharSequence? = null
        var suffix: CharSequence? = null

        constructor(state: Parcelable?) : super(state)

        constructor(parcel: Parcel) : super(parcel) {
            labelText = parcel.readString()

            isErrorEnable = parcel.readInt() == 1
            errorText = parcel.readString()

            prefix = parcel.readString()
            suffix = parcel.readString()
        }

        override fun writeToParcel(dest: Parcel, flags: Int) {
            super.writeToParcel(dest, flags)
            dest.writeString(labelText.toString())

            dest.writeInt(if (isErrorEnable) 1 else 0)
            dest.writeString(errorText.toString())

            dest.writeString(prefix.toString())
            dest.writeString(suffix.toString())
        }

        override fun describeContents() = 0
    }
}