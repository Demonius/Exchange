package by.dmitry.exchange.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.dmitry.exchange.base.fragment.BaseFragment
import by.dmitry.exchange.databinding.FragmentListCurrenciesBinding
import by.dmitry.exchange.view.activity.model.RateCurrencyModel
import by.dmitry.exchange.view.activity.model.RatesModelState
import by.dmitry.exchange.view.fragment.adapters.RateListener
import by.dmitry.exchange.view.fragment.adapters.RatesAdapter
import by.dmitry.exchange.view.fragment.adapters.RtesDiffUtilCallback
import by.dmitry.exchange.view.viewmodel.ExchangeViewModel
import javax.inject.Inject

class ListCurrenciesFragment : BaseFragment<FragmentListCurrenciesBinding>(), RateListener {

    @Inject
    lateinit var viewModel: ExchangeViewModel

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentListCurrenciesBinding = FragmentListCurrenciesBinding::inflate

    private val adapterRates = RatesAdapter(this, RtesDiffUtilCallback())

    override fun initView(savedInstanceState: Bundle?) {
        lifecycleScope.launchWhenResumed {
            viewModel.rates.collect { handleRates(it) }
        }

        binding.rvRates.apply {
            adapter = adapterRates
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    private fun handleRates(state: RatesModelState) {
        adapterRates.submitList(state.listRates)
    }

    companion object {
        fun newInstance() = ListCurrenciesFragment()
    }

    override fun changeStateFavorite(rateModel: RateCurrencyModel, isChecked: Boolean) {
        viewModel.changeFavoriteState(rateModel, isChecked)
    }
}