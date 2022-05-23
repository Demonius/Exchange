package by.dmitry.data.client

import android.annotation.SuppressLint
import android.content.Context
import by.dmitry.data.adapters.NetworkResponseAdapterFactory
import by.dmitry.data.client.interceptors.ApiKeyInterceptors
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

object RemoteClient {

    private const val CONNECTION_TIMEOUT = 90L
    private const val WRITE_TIMEOUT = 90L
    private const val READ_TIMEOUT = 90L

    private const val BASE_URL = "https://api.apilayer.com/exchangerates_data/"
    private const val TLS = "TLS"

    fun makeNetworkClient(appContext: Context): Retrofit {

        val trustManager = makeTrustManager()
        val sslSocketFactory = makeSslSocketFactory(trustManager)
        val httpLoggingInterceptor = makeLoggingInterceptor(true)

        val okHttpClient = makeOkHttpClient(
            sslSocketFactory, trustManager, httpLoggingInterceptor
        )
        return makeRetrofit(okHttpClient, BASE_URL)
    }

    private fun makeOkHttpClient(
        sslSocketFactory: SSLSocketFactory,
        trustManager: X509TrustManager,
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ) = OkHttpClient.Builder()
        .sslSocketFactory(sslSocketFactory, trustManager)
        .hostnameVerifier { _, _ -> true }
        .addNetworkInterceptor(httpLoggingInterceptor)
        .addInterceptor(ApiKeyInterceptors())
        .followRedirects(false)
        .followSslRedirects(false)
        .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        .build()

    private fun makeSslSocketFactory(trustManager: X509TrustManager): SSLSocketFactory {
        val sslContext = SSLContext.getInstance(TLS)
        sslContext.init(null, arrayOf(trustManager), SecureRandom())
        return sslContext.socketFactory
    }

    private fun makeRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
        val gson = GsonBuilder().create()
        val gsonConverter = GsonConverterFactory.create(gson)
        val callAdapter = NetworkResponseAdapterFactory()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(gsonConverter)
            .addCallAdapterFactory(callAdapter)
            .build()
    }

    private fun makeLoggingInterceptor(debug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level =
            if (debug) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return logging
    }

    private fun makeTrustManager() = getDebugTrustManager()

    @SuppressLint("TrustAllX509TrustManager")
    private fun getDebugTrustManager() = object : X509TrustManager {

        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        }

        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        }

        override fun getAcceptedIssuers() = arrayOf<X509Certificate>()
    }
}