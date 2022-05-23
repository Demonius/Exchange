package by.dmitry.exchange.base.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import by.dmitry.data.models.BaseResponse
import by.dmitry.data.models.Resource
import by.dmitry.exchange.base.tools.resources.IResourceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

abstract class BaseViewModel : ViewModel(), LifecycleObserver {

    @Inject
    lateinit var resourceManager: IResourceManager

    protected fun <M> updateState(stateFlow: MutableStateFlow<M>, resources: M) {
        stateFlow.value = resources
    }

    suspend fun <T> Flow<T>.collectWithCatch(collect: (data: T) -> Unit, error: ((exception: Throwable) -> Unit)? = null) {
        this
            .catch { exception ->
                error?.let { error.invoke(exception) }
            }
            .flowOn(Dispatchers.Main)
            .collect { collect.invoke(it) }
    }
}