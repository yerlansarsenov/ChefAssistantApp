package kz.spoonacular.data.api

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Sarsenov Yerlan on 30.01.2021.
 */
class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val httpUrl = request.url.newBuilder().addQueryParameter("apiKey", ApiKey.apiKey).build()
        request = request.newBuilder().url(httpUrl).build()
        return chain.proceed(request)
    }
}
