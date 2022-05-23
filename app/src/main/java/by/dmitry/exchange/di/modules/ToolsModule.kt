package by.dmitry.exchange.di.modules

import by.dmitry.exchange.base.tools.resources.IResourceManager
import by.dmitry.exchange.base.tools.resources.ResourceManager
import dagger.Binds
import dagger.Module

@Module
interface ToolsModule {

    @Binds
    fun bindsResourceManager(resourceManager: ResourceManager): IResourceManager
}