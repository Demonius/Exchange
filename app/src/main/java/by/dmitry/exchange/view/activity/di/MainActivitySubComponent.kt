package by.dmitry.exchange.view.activity.di

import by.dmitry.exchange.base.activity.PerActivity
import by.dmitry.exchange.view.activity.MainActivity
import by.dmitry.exchange.view.activity.di.fragmentBuilder.FragmentsMainActivityBuilder
import by.dmitry.exchange.view.activity.di.modules.MainActivityModule
import dagger.Subcomponent
import dagger.android.AndroidInjector

@PerActivity
@Subcomponent(modules = [MainActivityModule::class, FragmentsMainActivityBuilder::class])
interface MainActivitySubComponent : AndroidInjector<MainActivity> {

    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<MainActivity>
}