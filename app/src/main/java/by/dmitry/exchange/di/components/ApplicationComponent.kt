package by.dmitry.exchange.di.components

import android.app.Application
import by.dmitry.exchange.application.ExchangeApplication
import by.dmitry.exchange.di.modules.ApplicationModule
import by.dmitry.exchange.di.modules.FeatureModule
import by.dmitry.exchange.di.scope.PerApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@PerApplication
@Component(
    modules = [
        ApplicationModule::class,
        AndroidSupportInjectionModule::class,
        FeatureModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<ExchangeApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}