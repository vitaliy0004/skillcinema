package com.example.final_android_lvl1.data.retrofit

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("X-API-KEY", apiKey)
            .build()

        return chain.proceed(request)
    }
}