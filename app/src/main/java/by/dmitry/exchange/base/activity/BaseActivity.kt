package by.dmitry.exchange.base.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.CallSuper
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import by.dmitry.exchange.base.fragment.BaseFragment
import by.dmitry.exchange.base.widget.ProgressDialog
import by.dmitry.exchange.base.widget.ProgressDialogImpl
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.flow.StateFlow

abstract class BaseActivity<out VB : ViewBinding> : DaggerAppCompatActivity() {

    open val fragmentContainerId: Int? = null

    private var _binding: ViewBinding? = null

    protected val binding: VB
        get() = requireNotNull(_binding) as VB
    abstract val bindingInflater: (LayoutInflater) -> VB

    private lateinit var progressDialog: ProgressDialog

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(requireNotNull(_binding).root)
        initView()
        initProgressDialog()
        initViewModel()
    }

    open fun initViewModel() {}

    abstract fun initView()

    private fun initProgressDialog() {
        progressDialog = ProgressDialogImpl(this)
    }

    fun showLoading() {
        progressDialog.let { progressDialog.show() }
    }

    fun hideLoading() {
        progressDialog.let { progressDialog.hide() }
    }

    fun replaceFragment(fragment: BaseFragment<*>, isBackStack: Boolean = false) {
        fragmentContainerId?.let { fragId ->
            replaceFragment(fragId, fragment, isBackStack)
        } ?: throw IllegalArgumentException("fragment container id equals null")
    }

    fun replaceFragment(frameId: Int, fragment: BaseFragment<*>, isBackStack: Boolean = false) {
        val transaction = supportFragmentManager.beginTransaction()

        if (isBackStack) {
            transaction.addToBackStack(fragment.javaClass.simpleName)
        }
        transaction.replace(frameId, fragment)
        transaction.commit()
    }

    open fun showInternetConnectionLost() {}
}