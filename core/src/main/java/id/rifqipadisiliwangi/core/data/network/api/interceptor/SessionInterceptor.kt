package id.rifqipadisiliwangi.core.data.network.api.interceptor

import id.rifqipadisiliwangi.core.data.local.datastore.datasource.AppPreferenceDataSource
import okhttp3.Interceptor
import okhttp3.Response

class SessionInterceptor(private val appPrefs : AppPreferenceDataSource) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return chain.proceed(request)
    }

}