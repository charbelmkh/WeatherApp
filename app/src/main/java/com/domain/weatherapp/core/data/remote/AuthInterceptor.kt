package com.domain.weatherapp.core.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * A {@see RequestInterceptor} that adds an auth token to requests
 */
class AuthInterceptor(private val accessToken: String) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val httpUrl=original.url
        val newUrl=httpUrl.newBuilder().addQueryParameter("appid",accessToken).build()
        val request=original.newBuilder().url(newUrl).build()
        return chain.proceed(request)
    }
}
