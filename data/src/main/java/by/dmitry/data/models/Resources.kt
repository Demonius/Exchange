package by.dmitry.data.models

sealed class Resource<T : BaseResponse>(open val status: Status, open val data: T?, open val message: String?) {
    data class SuccessResource<T : BaseResponse>(override val data: T?) : Resource<T>(Status.SUCCESS, data, null)
    data class ErrorResource<T : BaseResponse>(override val message: String? = null, override val data: T? = null) : Resource<T>(Status.ERROR, data, message)
    data class LoadingResource<T : BaseResponse>(override val data: T? = null, override val message: String? = null) : Resource<T>(Status.LOADING, data, message)
    data class FinishResource<T : BaseResponse>(override val data: T?) : Resource<T>(Status.FINISH, data, null)
}
