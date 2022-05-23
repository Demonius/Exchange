package by.dmitry.exchange.base.widget

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatDialog
import by.dmitry.exchange.R

class ProgressDialogImpl(context: Context) : ProgressDialog {

    private var progressDialog: AppCompatDialog = AppCompatDialog(context)

    init {
        progressDialog.setContentView(R.layout.dialog_progress)
        progressDialog.setCancelable(false)
        progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun show() {
        if (progressDialog.isShowing) return

        progressDialog.show()
    }

    override fun hide() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }
}