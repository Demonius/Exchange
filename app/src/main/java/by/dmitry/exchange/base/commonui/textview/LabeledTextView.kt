package by.dmitry.exchange.base.commonui.textview

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.text.Html
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import by.dmitry.exchange.R
import com.google.android.material.textview.MaterialTextView

class LabeledTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    companion object {
        private const val NULL_INT = -1
        private const val NULL_FLOAT = -1f
        private const val DEFAULT_TEXT_STYLE = 0
    }

    private lateinit var labelView: MaterialTextView
    private lateinit var textView: MaterialTextView

    var isLabelVisible: Boolean
        set(value) {
            labelView.isVisible = value
        }
        get() = labelView.isVisible

    var isTextVisible: Boolean
        set(value) {
            textView.isVisible = value
        }
        get() {
            return textView.isVisible
        }

    var labelText: CharSequence
        set(state) {
            labelView.text = state
        }
        get() = labelView.text

    var text: CharSequence
        set(state) {
            textView.text = state
        }
        get() = textView.text.toString()

    var textSize: Float
        set(value) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, value)
        }
        get() {
            return textView.textSize
        }
    var labeleSize: Float
        set(value) {
            labelView.setTextSize(TypedValue.COMPLEX_UNIT_PX, value)
        }
        get() {
            return labelView.textSize
        }

    var typefase: Typeface
        set(value) {
            textView.typeface = value
        }
        get() {
            return textView.typeface
        }

    var gravityText: Int
        set(value) {
            textView.gravity = value
            labelView.gravity = value
        }
        get() = textView.gravity

    init {
        isSaveEnabled = true
        orientation = VERTICAL
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)

        inflate(context, R.layout.view_text_view, this)

        initViews()
        initAttrs(attrs)
    }

    private fun initViews() {
        labelView = findViewById(R.id.tv_title)
        textView = findViewById(R.id.tv_text)
    }

    fun setLabelText(@StringRes stringRes: Int) {
        labelText = getString(stringRes)
    }

    fun setText(@StringRes stringRes: Int) {
        text = getString(stringRes)
    }

    fun setHtmlLabel(htmlText: String) {
        labelText = convertHtmlToString(htmlText)
    }

    fun setHtmlLabel(@StringRes idRes: Int) {
        labelText = convertHtmlToString(getString(idRes))
    }

    fun setHtmlText(htmlText: String) {
        text = convertHtmlToString(htmlText)
    }

    private fun convertHtmlToString(htmlText: String) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(htmlText)
        }

    private fun getString(@StringRes stringRes: Int) = resources.getString(stringRes)

    fun setTextColor(@ColorInt color: Int) {
        textView.setTextColor(color)
    }

    private fun initAttrs(attrs: AttributeSet?) {
        context.withStyledAttributes(attrs, R.styleable.TextInputView) {
            getString(R.styleable.TextInputView_title)?.let { labelText = it }
            getString(R.styleable.TextInputView_text)?.let { text = it }

            if (hasValue(R.styleable.TextInputView_android_textAppearance))
                setTextApperance(
                    getResourceId(R.styleable.TextInputView_android_textAppearance, NULL_INT)
                )
            getInt(R.styleable.TextInputView_android_textStyle, DEFAULT_TEXT_STYLE).let {
                val currentTypeface = textView.typeface
                textView.setTypeface(currentTypeface, it)
            }

            getDimension(R.styleable.TextInputView_android_textSize, NULL_FLOAT).let {
                if (it == NULL_FLOAT) return
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, it)
            }

            getInt(R.styleable.TextInputView_android_gravity, NULL_INT).let {
                textView.gravity = it
            }
        }
    }

    private fun setTextApperance(resourceId: Int) {
        if (resourceId == NULL_INT) return

        textView.setTextAppearance(resourceId)
    }

    override fun onSaveInstanceState(): Parcelable =
        SavedState(super.onSaveInstanceState()).apply {
            this.titleText = this@LabeledTextView.labelText
            this.text = this@LabeledTextView.text
            this.isLabelVisible = this@LabeledTextView.isLabelVisible
        }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val savedState = state as SavedState
        super.onRestoreInstanceState(savedState.superState)

        labelText = savedState.titleText.toString()
        text = savedState.text.toString()
        isLabelVisible = savedState.isLabelVisible ?: false

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

        var titleText: CharSequence? = null
        var text: CharSequence? = null
        var isLabelVisible: Boolean? = null

        constructor(state: Parcelable?) : super(state)

        constructor(parcel: Parcel) : super(parcel) {
            titleText = parcel.readString()
            text = parcel.readString()
            isLabelVisible = parcel.readInt() == 1
        }

        override fun writeToParcel(dest: Parcel, flags: Int) {
            super.writeToParcel(dest, flags)
            dest.writeString(titleText.toString())
            dest.writeString(text.toString())
            val isVisibleInt = if (isLabelVisible == true) 1 else 0
            dest.writeInt(isVisibleInt)
        }

        override fun describeContents() = 0
    }
}