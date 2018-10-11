package me.hackathon.monicershopsapp.di

import android.annotation.SuppressLint
import android.app.Application
import dagger.Module
import dagger.Provides
import me.hackathon.monicershopsapp.network.LiveDataCallAdapterFactory
import me.hackathon.monicershopsapp.network.ApiService
import me.hackathon.monicershopsapp.network.TokenHeaderInterceptor
import me.hackathon.monicershopsapp.util.Constants.BASE_URL
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideCache(cacheFile: File): Cache = Cache(cacheFile, 10 * 1024 * 1024)

    @Provides
    @Singleton
    fun provideCacheFile(context: Application): File = File(context.cacheDir, "okhttp_cache")

    @Provides
    @Singleton
    fun provideTokenHeaderInterceptor(): TokenHeaderInterceptor = TokenHeaderInterceptor()

    @SuppressLint("TrustAllX509TrustManager")
    @Singleton
    @Provides
    fun provideOkHttpClient(authenticationInterceptor: TokenHeaderInterceptor, cache: Cache): OkHttpClient {
        val allowAllTrustManagers = object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate>? {
                return emptyArray()
            }
        }

        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, arrayOf(allowAllTrustManagers), SecureRandom())
        val sslSocketFactory = sslContext.socketFactory

        return OkHttpClient.Builder()
                .addInterceptor(authenticationInterceptor)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .sslSocketFactory(sslSocketFactory, allowAllTrustManagers)
                .hostnameVerifier { _, _ -> true }
                .cache(cache)
                .retryOnConnectionFailure(true)
                .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
