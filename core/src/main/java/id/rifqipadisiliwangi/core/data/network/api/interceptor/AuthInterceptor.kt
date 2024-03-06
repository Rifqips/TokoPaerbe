package id.rifqipadisiliwangi.core.data.network.api.interceptor

import id.rifqipadisiliwangi.core.BuildConfig
import id.rifqipadisiliwangi.core.data.local.datastore.datasource.AppPreferenceDataSource
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val preference: AppPreferenceDataSource) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val modifiedRequest = when(request.url.encodedPath) {
            "/login", "/register", "/refresh" -> {
                request
                    .newBuilder()
                    .addHeader("API_KEY", BuildConfig.API_KEY)
                    .build()
            }
            else -> {
                runBlocking {
                    val accesToken = preference.getUserToken()
                    request
                        .newBuilder()
                        .addHeader("Authorization", "Bearer $accesToken")
                        .build()
                }}
        }
        return chain.proceed(modifiedRequest)
    }
}