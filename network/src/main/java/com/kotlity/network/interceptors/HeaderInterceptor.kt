package com.kotlity.network.interceptors

import com.kotlity.network.di.annotations.HeaderRequestInterceptorQualifier
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor(
    @HeaderRequestInterceptorQualifier
    private val requestInterceptor: RequestInterceptor
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = requestInterceptor.intercept(chain)
}