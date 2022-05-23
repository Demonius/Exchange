package by.dmitry.exchange.view.activity

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.AutoCompleteTextView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import by.dmitry.domain.model.CurrencyExchange
import by.dmitry.exchange.R
import by.dmitry.exchange.base.activity.BaseActivity
import by.dmitry.exchange.base.activity.viewModel
import by.dmitry.exchange.base.commonui.edittext.BaseInputView
import by.dmitry.exchange.base.commonui.textview.wtchers.InputTextWatcher
import by.dmitry.exchange.databinding.ActivityMainBinding
import by.dmitry.exchange.view.activity.adapters.EnterCurrenciesAdapter
import by.dmitry.exchange.view.activity.model.CurrenciesModelState
import by.dmitry.exchange.view.fragment.FavoriteCurrenciesFragment
import by.dmitry.exchange.view.fragment.ListCurrenciesFragment
import by.dmitry.exchange.view.fragment.SortFragment
import by.dmitry.exchange.view.viewmodel.ExchangeViewModel
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding>(), NavigationBarView.OnItemSelectedListener, View.OnClickListener, View.OnFocusChangeListener {

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding = ActivityMainBinding::inflate

    override val fragmentContainerId = R.id.flMainContainer

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: ExchangeViewModel

    private var isInitializeAutocomplete = false

    private val enterCurrencyTextWatcher = InputTextWatcher { value ->
        viewModel.setChoosedCurrency(value, binding.inputCurrency.isErrorEnable)
        handleErrorStateForInput(binding.inputCurrency)
    }

    private var defaultTab = R.id.menu_popular

    override fun initView() {

        binding.actionSort.setOnClickListener(this)

        binding.inputCurrency.apply {
            addOnTextChangeListener(enterCurrencyTextWatcher)
            threshold = 1
            addFocusChangeListener(this@MainActivity)
            addOnClickListener { binding.inputCurrency.showExpandedList() }
        }

        with(binding.navBar) {
            setOnItemSelectedListener(this@MainActivity)
            selectedItemId = defaultTab
        }
    }

    override fun initViewModel() {
        viewModel = viewModel(viewModelFactory) {
            lifecycleScope.launchWhenResumed {
                currencyListState.collect { handleStateCurrencies(it) }
            }
            getAllCurrencies()
        }
    }

    private fun handleStateCurrencies(state: CurrenciesModelState) {
        if (state.isLoading) {
            showLoading()
            return
        } else {
            hideLoading()
        }

        if (!state.error.isNullOrEmpty()) {
            Snackbar.make(binding.llToolbar, state.error.toString(), Snackbar.LENGTH_LONG).show()
            return
        }

        if (state.listCurrencies.isNotEmpty() && !isInitializeAutocomplete) {
            initializeAutocompleteEditText(state.listCurrencies)
            isInitializeAutocomplete = true
        }
    }

    private fun initializeAutocompleteEditText(listCurrencies: List<CurrencyExchange>) {
        val list = ArrayList(listCurrencies)
        val adapter = EnterCurrenciesAdapter(
            this, list,
            { item ->
                binding.inputCurrency.apply {
                    text = item.abbr
                    closeExpandedList()
                }
            },
            { isEmpty ->
                binding.inputCurrency.apply {
                    errorText = getString(R.string.error_currency_input)
                    isErrorEnable = isEmpty
                }
            }
        )
        binding.inputCurrency.setAdapter(adapter)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_favorite -> {
                replaceFragment(FavoriteCurrenciesFragment.newInstance())
                showSelectCurrency()
                true
            }
            R.id.menu_popular -> {
                replaceFragment(ListCurrenciesFragment.newInstance())
                showSelectCurrency()
                true
            }

            else -> false
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.actionSort -> {
                hideSelectCurrency()
                replaceFragment(SortFragment.newInstance(), false)
            }
        }
    }

    private fun hideSelectCurrency() {
        with(binding.llToolbar) {
            if (isVisible) {
                isVisible = false
            }
        }
    }

    private fun showSelectCurrency() {
        with(binding.llToolbar) {
            if (isGone) {
                isVisible = true
            }
        }
    }

    private fun handleErrorStateForInput(view: BaseInputView<*>) {
        if (view.isErrorEnable) view.isErrorEnable = false
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (v is AutoCompleteTextView && hasFocus) v.showDropDown()
    }
}