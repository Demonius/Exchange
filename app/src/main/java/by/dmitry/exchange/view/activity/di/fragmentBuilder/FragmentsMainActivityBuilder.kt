package by.dmitry.exchange.view.activity.di.fragmentBuilder

import by.dmitry.exchange.view.activity.di.fragmentsubcomponent.FavoriteFragmentSubComponent
import by.dmitry.exchange.view.activity.di.fragmentsubcomponent.ListFragmentSubComponent
import by.dmitry.exchange.view.activity.di.fragmentsubcomponent.SortFragmentSubComponent
import by.dmitry.exchange.view.fragment.FavoriteCurrenciesFragment
import by.dmitry.exchange.view.fragment.ListCurrenciesFragment
import by.dmitry.exchange.view.fragment.SortFragment
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(
    subcomponents = [
        ListFragmentSubComponent::class,
        FavoriteFragmentSubComponent::class,
        SortFragmentSubComponent::class
    ]
)
interface FragmentsMainActivityBuilder {

    @Binds
    @IntoMap
    @ClassKey(ListCurrenciesFragment::class)
    fun bindsListCurrenciesFragmentFactory(factory: ListFragmentSubComponent.Factory): AndroidInjector.Factory<*>

    @Binds
    @IntoMap
    @ClassKey(FavoriteCurrenciesFragment::class)
    fun bindsFavoritesCurrenciesFragmentFactory(factory: FavoriteFragmentSubComponent.Factory): AndroidInjector.Factory<*>

    @Binds
    @IntoMap
    @ClassKey(SortFragment::class)
    fun bindsSortFragmentFactory(factory: SortFragmentSubComponent.Factory): AndroidInjector.Factory<*>
}