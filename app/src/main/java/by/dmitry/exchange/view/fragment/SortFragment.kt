package by.dmitry.exchange.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.lifecycle.lifecycleScope
import by.dmitry.exchange.R
import by.dmitry.exchange.base.fragment.BaseFragment
import by.dmitry.exchange.databinding.FragmentConfigurationSortBinding
import by.dmitry.exchange.view.activity.model.CurrenciesModelState
import by.dmitry.exchange.view.viewmodel.ExchangeViewModel
import javax.inject.Inject

class SortFragment : BaseFragment<FragmentConfigurationSortBinding>(), CompoundButton.OnCheckedChangeListener {
    @Inject
    lateinit var viewModel: ExchangeViewModel

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentConfigurationSortBinding =
        FragmentConfigurationSortBinding::inflate

    override fun initView(savedInstanceState: Bundle?) {
        lifecycleScope.launchWhenResumed {
            viewModel.currencyListState.collect { handleStateCurrencies(it) }
        }
    }

    private fun handleStateCurrencies(state: CurrenciesModelState) {
        when (state.sortConfiguration) {
            SortConfiguration.ALPHABET_INCRISE -> binding.radioAlphabetIncrise.isChecked = true
            SortConfiguration.ALPHABET_DECRISE -> binding.radioAlphabetDecrise.isChecked = true
            SortConfiguration.VALUE_INCRISE -> binding.radioValueIncrise.isChecked = true
            SortConfiguration.VALUE_DECRISE -> binding.radioValueDecrise.isChecked = true
            null -> {}
        }
        initRadioCheckedListener()
    }

    private fun initRadioCheckedListener() {
        with(binding) {
            radioAlphabetIncrise.setOnCheckedChangeListener(this@SortFragment)
            radioAlphabetDecrise.setOnCheckedChangeListener(this@SortFragment)
            radioValueIncrise.setOnCheckedChangeListener(this@SortFragment)
            radioValueDecrise.setOnCheckedChangeListener(this@SortFragment)
        }
    }

    companion object {
        fun newInstance() = SortFragment()
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when (buttonView?.id) {
            R.id.radioAlphabetIncrise -> handleChooseSortConfiguration(SortConfiguration.ALPHABET_INCRISE)
            R.id.radioAlphabetDecrise -> handleChooseSortConfiguration(SortConfiguration.ALPHABET_DECRISE)
            R.id.radioValueIncrise -> handleChooseSortConfiguration(SortConfiguration.VALUE_INCRISE)
            R.id.radioValueDecrise -> handleChooseSortConfiguration(SortConfiguration.VALUE_DECRISE)
        }
    }

    private fun handleChooseSortConfiguration(sortConfiguration: SortConfiguration) {
        viewModel.setSortConfiguration(sortConfiguration)
        removeRadioCheckedListeners()

        with(binding) {
            if (sortConfiguration != SortConfiguration.ALPHABET_INCRISE) {
                radioAlphabetIncrise.isChecked = false
            }
            if (sortConfiguration != SortConfiguration.ALPHABET_DECRISE) {
                radioAlphabetDecrise.isChecked = false
            }
            if (sortConfiguration != SortConfiguration.VALUE_INCRISE) {
                radioValueIncrise.isChecked = false
            }
            if (sortConfiguration != SortConfiguration.VALUE_DECRISE) {
                radioValueDecrise.isChecked = false
            }
        }

        initRadioCheckedListener()
    }

    private fun removeRadioCheckedListeners() {
        with(binding) {
            radioAlphabetIncrise.setOnCheckedChangeListener(null)
            radioAlphabetDecrise.setOnCheckedChangeListener(null)
            radioValueIncrise.setOnCheckedChangeListener(null)
            radioValueDecrise.setOnCheckedChangeListener(null)
        }
    }

    enum class SortConfiguration {
        ALPHABET_INCRISE, ALPHABET_DECRISE, VALUE_INCRISE, VALUE_DECRISE
    }
}

