package id.rifqipadisiliwangi.core.data.network.api.interceptor

import com.chuckerteam.chucker.api.ChuckerInterceptor
import id.rifqipadisiliwangi.core.BuildConfig
import id.rifqipadisiliwangi.core.data.local.datastore.datasource.AppPreferenceDataSource
import id.rifqipadisiliwangi.core.data.network.api.model.refresh.RequestRefreshToken
import id.rifqipadisiliwangi.core.data.network.api.model.refresh.ResponseRefreshToken
import id.rifqipadisiliwangi.core.data.network.api.service.ApplicationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TokenInterceptor(
    private val appPrefeces : AppPreferenceDataSource,
    private val chuckerInterceptor: ChuckerInterceptor
): okhttp3.Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        var refreshToken = ""
        GlobalScope.launch(Dispatchers.IO){
            refreshToken = appPrefeces.getRefreshToken()
        }
        synchronized(this){
            return runBlocking {
                try {
                    val newToken = refreshToken
                    appPrefeces.saveRefreshToken(refreshToken)
                    appPrefeces.saveUserToken(newToken)
                    response.request
                        .newBuilder()
                        .header("Authorization", "Bearer $newToken")
                        .build()
                }catch (error : Throwable){
                    response.close()
                    null
                }
            }
        }
    }

    private suspend fun refreshToken(tokenRequest : RequestRefreshToken): ResponseRefreshToken {
        val interceptor = Interceptor.invoke {chain ->
            val request = chain
                .request()
                .newBuilder()
                .addHeader("API_KEY", BuildConfig.API_KEY)
                .build()
            chain.proceed(request)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(chuckerInterceptor)
            .addInterceptor(interceptor)
            .build()

        val apiService = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApplicationService::class.java)

        try {
            val newRequest = apiService.fetchRefreshToken(tokenRequest)
            appPrefeces.saveUserToken(newRequest.data.accessToken)
            appPrefeces.saveRefreshToken(newRequest.data.refreshToken)
            return newRequest
        }catch (err : Exception){
            throw Exception(err.message)
        }
    }

}