package by.dmitry.exchange.base.commonui.edittext

import android.content.Context
import android.text.method.KeyListener
import android.util.AttributeSet
import android.widget.EditText
import android.widget.LinearLayout
import androidx.annotation.StringRes
import by.dmitry.exchange.base.commonui.textview.LabeledTextView
import com.google.android.material.textfield.TextInputLayout

abstract class BaseInputView<E : EditText> @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {


    companion object {
        private const val EMPTY_LABEL = ""
        internal const val NULL_ACTION_ID = 0

    }

    protected lateinit var title: LabeledTextView
    protected lateinit var inputLayout: TextInputLayout
    protected lateinit var textView: E

    var titleText: CharSequence
        set(state) {
            title.labelText = state
        }
        get() {
            return title.labelText
        }

    var text: CharSequence
        set(state) {
            textView.setText(state)
        }
        get() {
            return textView.text.toString()
        }

    var isErrorEnable: Boolean
        set(value) {
            inputLayout.isErrorEnabled = value
        }
        get() {
            return inputLayout.isErrorEnabled
        }

    var errorText: CharSequence?
        set(value) {
            inputLayout.error = value
        }
        get() {
            return inputLayout.error
        }

    var helperText: CharSequence?
        set(value) {
            inputLayout.helperText = value
        }
        get() {
            return inputLayout.helperText
        }

    var isHelperTextEnabled: Boolean
        set(value) {
            inputLayout.isHelperTextEnabled = value
        }
        get() {
            return inputLayout.isHelperTextEnabled
        }

    var prefix: CharSequence?
        set(value) {
            inputLayout.prefixText = value
        }
        get() {
            return inputLayout.prefixText
        }

    var suffix: CharSequence?
        set(value) {
            inputLayout.suffixText = value
        }
        get() {
            return inputLayout.suffixText
        }

    var hint: CharSequence?
        set(value) {
            textView.hint = value
        }
        get() {
            return textView.hint
        }

    var counterMaxLength: Int
        set(value) {
            inputLayout.counterMaxLength = value
        }
        get() {
            return inputLayout.counterMaxLength
        }

    var isCounterEnabled: Boolean
        set(value) {
            inputLayout.isCounterEnabled = value
        }
        get() {
            return inputLayout.isCounterEnabled
        }

    var isEnable: Boolean
        set(value) {
            inputLayout.isEnabled = value
            textView.isEnabled = value
        }
        get() {
            return textView.isEnabled
        }

    var keyListener: KeyListener
        set(value) {
            textView.keyListener = value
        }
        get() = textView.keyListener


    fun setLabelText(@StringRes stringRes: Int) {
        titleText = getString(stringRes)
    }

    fun setText(@StringRes stringRes: Int) {
        text = getString(stringRes)
    }

    fun setPrefixText(@StringRes stringRes: Int) {
        prefix = getString(stringRes)
    }

    fun setSuffixText(@StringRes stringRes: Int) {
        suffix = getString(stringRes)
    }

    fun setErrorText(@StringRes stringRes: Int) {
        errorText = getString(stringRes)
    }

    fun setHint(@StringRes stringRes: Int) {
        hint = getString(stringRes)
    }

    fun appendText(text: String) {
        textView.append(text)
    }

    fun setImeActionId(actionId: Int) {
        textView.setImeActionLabel(EMPTY_LABEL, actionId)
    }

    fun setImeOptions(imeOptions: Int) {
        textView.imeOptions = imeOptions
    }

    fun setSelection(index: Int) {
        textView.setSelection(index)
    }

    fun getSelectionStart() = textView.selectionStart

    fun getSelectionEnd() = textView.selectionEnd

    fun requestFocusEditText() {
        textView.requestFocus()
    }


    protected fun getString(@StringRes stringRes: Int) = resources.getString(stringRes)
}