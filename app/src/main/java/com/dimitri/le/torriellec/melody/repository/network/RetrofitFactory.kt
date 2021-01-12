package com.dimitri.le.torriellec.melody.repository.network

import android.content.Context
import com.dimitri.le.torriellec.melody.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit


object RetrofitFactory {

    fun buildHttpClient(context: Context): OkHttpClient {
        val httpCacheDirectory = File(context.cacheDir, "offlineCache")
        //10 MB
        val cache = Cache(httpCacheDirectory, 10 * 1024 * 1024)
        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(buildHttpLoggingInterceptor())
            .addNetworkInterceptor(provideCacheInterceptor())
            .addInterceptor(provideOfflineCacheInterceptor())
            .build()
    }

    private fun buildHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        }
    }

    fun buildMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    private fun provideCacheInterceptor(): Interceptor {
        return object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                var request = chain.request()
                val originalResponse = chain.proceed(request)
                val cacheControl = originalResponse.header("Cache-Control")

                if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains(
                        "no-cache"
                    ) ||
                    cacheControl.contains("must-revalidate") || cacheControl.contains("max-stale=0")
                ) {

                    val cc = CacheControl.Builder()
                        .maxStale(1, TimeUnit.DAYS)
                        .build()

                    request = request.newBuilder()
                        .cacheControl(cc)
                        .build()

                    return chain.proceed(request)

                } else {
                    return originalResponse
                }
            }
        }
    }

    private fun provideOfflineCacheInterceptor(): Interceptor {
        return object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                return try {
                    chain.proceed(chain.request())
                } catch (e: Exception) {
                    val cacheControl = CacheControl.Builder()
                        .onlyIfCached()
                        .maxStale(1, TimeUnit.DAYS)
                        .build()
                    val offlineRequest = chain.request().newBuilder()
                        .cacheControl(cacheControl)
                        .build()
                    chain.proceed(offlineRequest)
                }
            }

        }
    }
}
