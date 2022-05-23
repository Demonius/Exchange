package by.dmitry.exchange.view.activity.di.modules

import androidx.lifecycle.ViewModel
import by.dmitry.domain.model.CurrencyExchange
import by.dmitry.domain.usecase.BaseUseCase
import by.dmitry.domain.usecase.CompletableUseCase
import by.dmitry.domain.usecase.GetAllCurrenciesFromDbUseCase
import by.dmitry.domain.usecase.UpdateAllCurrenciesUseCase
import by.dmitry.exchange.base.activity.PerActivity
import by.dmitry.exchange.base.viewmodel.ViewModelKey
import by.dmitry.exchange.view.viewmodel.ExchangeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kotlinx.coroutines.flow.Flow

@Module
interface MainActivityModule {

    @Binds
    @IntoMap
    @PerActivity
    @ViewModelKey(ExchangeViewModel::class)
    fun bindsExchangeViewModel(viewModel: ExchangeViewModel): ViewModel
}