package by.dmitry.exchange.application

import android.content.Context
import androidx.multidex.MultiDex
import by.dmitry.exchange.di.components.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class ExchangeApplication: DaggerApplication(){

    private val daggerComponent by lazy {
        DaggerApplicationComponent.builder()
            .application(this)
            .build()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication>  = daggerComponent

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        initMultiDex(base)
    }

    private fun initMultiDex(base: Context) {
        MultiDex.install(base)
    }
}