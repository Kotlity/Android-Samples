package com.kotlity.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response

interface RequestInterceptor {

    fun intercept(chain: Interceptor.Chain): Response
}