package by.dmitry.exchange.di.modules

import by.dmitry.exchange.view.activity.MainActivity
import by.dmitry.exchange.view.activity.di.MainActivitySubComponent
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(
    subcomponents = [
        MainActivitySubComponent::class
    ]
)
interface FeatureModule {

    @Binds
    @IntoMap
    @ClassKey(MainActivity::class)
    fun bindsMainActivityFactory(factory: MainActivitySubComponent.Factory): AndroidInjector.Factory<*>
}