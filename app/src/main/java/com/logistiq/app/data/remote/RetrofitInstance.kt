package com.logistiq.app.data.remote

import com.logistiq.app.network.model.AuthApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.*

object RetrofitInstance {

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val trustAllCerts = arrayOf<TrustManager>(
        object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
        }
    )

    private fun createTrustAllSSL(): SSLSocketFactory {
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())
        return sslContext.socketFactory
    }

    private val unsafeClient = OkHttpClient.Builder()
        .sslSocketFactory(createTrustAllSSL(), trustAllCerts[0] as X509TrustManager)
        .hostnameVerifier { _, _ -> true }
        .addInterceptor(logging)
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://10.0.2.2:8000/")
            .client(unsafeClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: AuthApi by lazy {
        retrofit.create(AuthApi::class.java)
    }
}
