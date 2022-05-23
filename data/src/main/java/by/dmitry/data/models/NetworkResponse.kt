package by.dmitry.data.models

import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.*
import java.lang.reflect.Type

class NetworkResponseCall<S : BaseResponse, E : Any>(
    private val delegate: Call<S>,
    private val errorConverter: Converter<ResponseBody, E>
) : Call<Resource<S>> {

    override fun clone(): Call<Resource<S>> = NetworkResponseCall(delegate.clone(), errorConverter)

    override fun execute(): Response<Resource<S>> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun enqueue(callback: Callback<Resource<S>>) {
        return delegate.enqueue(object : Callback<S> {
            override fun onResponse(call: Call<S>, response: Response<S>) {
                val body = response.body()
                val code = response.code()
                val error = response.errorBody()

                if (response.isSuccessful) {
                    if (body != null) {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(Resource.SuccessResource(body))
                        )
                    } else {
                        // Response is successful but the body is null
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(Resource.ErrorResource<S>(error.toString()))
                        )
                    }
                } else {
                    val errorBody = when {
                        error == null -> null
                        error.contentLength() == 0L -> null
                        else -> try {
                            errorConverter.convert(error)
                        } catch (ex: Exception) {
                            null
                        }
                    }
                    if (errorBody != null) {
                        val errorText = StringBuilder().append("code: $code + ").append(errorBody)
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(Resource.ErrorResource<S>(errorText.toString()))
//                                NetworkResponse.ApiError(errorBody, code))
                        )
                    } else {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(Resource.ErrorResource<S>(error.toString()))
                        )
                    }
                }
            }

            override fun onFailure(call: Call<S>, t: Throwable) {
                val networkResponse = Resource.ErrorResource<S>(t.message)
                callback.onResponse(this@NetworkResponseCall, Response.success(networkResponse))
            }
        })
    }

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun cancel() {
        delegate.cancel()
    }

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}

class NetworkResponseAdapter<S : BaseResponse, E : Any>(
    private val successType: Type,
    private val errorBodyConverter: Converter<ResponseBody, E>
) : CallAdapter<S, Call<Resource<S>>> {

    override fun responseType(): Type = successType

    override fun adapt(call: Call<S>): Call<Resource<S>> {
        return NetworkResponseCall(call, errorBodyConverter)
    }
}