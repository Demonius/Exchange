package by.dmitry.exchange.view.activity.di.fragmentsubcomponent

import by.dmitry.exchange.base.fragment.PerFragment
import by.dmitry.exchange.view.fragment.FavoriteCurrenciesFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

@PerFragment
@Subcomponent(modules = [FavoriteFragmentModule::class])
interface FavoriteFragmentSubComponent : AndroidInjector<FavoriteCurrenciesFragment> {

    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<FavoriteCurrenciesFragment>
}