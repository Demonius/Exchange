package by.dmitry.exchange.view.activity.di.fragmentsubcomponent

import by.dmitry.exchange.base.fragment.PerFragment
import by.dmitry.exchange.view.fragment.SortFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

@PerFragment
@Subcomponent(modules = [SortFragmentModule::class])
interface SortFragmentSubComponent : AndroidInjector<SortFragment> {

    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<SortFragment>
}