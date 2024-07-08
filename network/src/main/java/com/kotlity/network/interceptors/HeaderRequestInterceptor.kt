package com.kotlity.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class HeaderRequestInterceptor(private val headerName: String, private val headerValue: String): RequestInterceptor {

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        val oldRequest = request()
        val newRequest = oldRequest.newBuilder()
            .addHeader(headerName, headerValue)
            .build()
        proceed(newRequest)
    }
}