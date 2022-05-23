package by.dmitry.exchange.view.activity.di.fragmentsubcomponent

import by.dmitry.exchange.base.fragment.PerFragment
import by.dmitry.exchange.view.fragment.ListCurrenciesFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

@PerFragment
@Subcomponent(modules = [ListFragmentModule::class])
interface ListFragmentSubComponent : AndroidInjector<ListCurrenciesFragment> {

    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<ListCurrenciesFragment>
}