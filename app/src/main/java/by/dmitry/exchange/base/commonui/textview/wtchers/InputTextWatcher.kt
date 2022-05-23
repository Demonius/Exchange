package by.dmitry.exchange.base.commonui.textview.wtchers

import android.text.Editable
import android.text.TextWatcher

class InputTextWatcher(private val listener: (s: String) -> Unit = { _ -> }) : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(s: Editable?) {
        listener.invoke(s.toString())
    }
}