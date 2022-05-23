package by.dmitry.data.client.interceptors

import okhttp3.*

class ApiKeyInterceptors : Interceptor {

    companion object {
        private const val NAME_QUERY_API_KEY = "apikey"
        private const val API_KEY = "A9P4szXVh5Mo9gPNBIQBWGoCxIPxSc5E"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()

        val headers = original.headers
        val headersBuilder = Headers.Builder().addAll(headers).add(NAME_QUERY_API_KEY, API_KEY)

        val requestBuilder: Request.Builder = original.newBuilder()
            .headers(headersBuilder.build())

        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }
}