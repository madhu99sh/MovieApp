package com.saveo.moviesapp.data

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class HeaderInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("x-rapidapi-key", "773912f8b3mshc1ca5d483adcef7p1e0bb9jsnc55e6e671efa")
            .addHeader("x-rapidapi-host", "devru-bigflix-movies-download-v1.p.rapidapi.com")
            .build()
        val response: Response
        response = chain.proceed(request)
        return response
    }
}