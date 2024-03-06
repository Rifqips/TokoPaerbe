package id.rifqipadisiliwangi.core.data.network.api.service

import com.chuckerteam.chucker.api.ChuckerInterceptor
import id.rifqipadisiliwangi.core.BuildConfig
import id.rifqipadisiliwangi.core.data.network.api.interceptor.AuthInterceptor
import id.rifqipadisiliwangi.core.data.network.api.interceptor.SessionInterceptor
import id.rifqipadisiliwangi.core.data.network.api.interceptor.TokenInterceptor
import id.rifqipadisiliwangi.core.data.network.api.model.detail.ResponseDetailStore
import id.rifqipadisiliwangi.core.data.network.api.model.fulfillment.RequestFullfilment
import id.rifqipadisiliwangi.core.data.network.api.model.fulfillment.ResponseFulfillment
import id.rifqipadisiliwangi.core.data.network.api.model.history.ResponseHistory
import id.rifqipadisiliwangi.core.data.network.api.model.login.RequestLoginItem
import id.rifqipadisiliwangi.core.data.network.api.model.login.ResponseLoginItem
import id.rifqipadisiliwangi.core.data.network.api.model.profile.ResponseRegisterProfile
import id.rifqipadisiliwangi.core.data.network.api.model.rating.RequestRating
import id.rifqipadisiliwangi.core.data.network.api.model.rating.ResponseRating
import id.rifqipadisiliwangi.core.data.network.api.model.refresh.RequestRefreshToken
import id.rifqipadisiliwangi.core.data.network.api.model.refresh.ResponseRefreshToken
import id.rifqipadisiliwangi.core.data.network.api.model.register.RequestRegisterItem
import id.rifqipadisiliwangi.core.data.network.api.model.register.ResponseRegisterItem
import id.rifqipadisiliwangi.core.data.network.api.model.review.ResponseReviewItem
import id.rifqipadisiliwangi.core.data.network.api.model.search.ResponseSearch
import id.rifqipadisiliwangi.core.data.network.api.model.store.ResponseStore
import id.rifqipadisiliwangi.core.utils.Constant.DETAIL
import id.rifqipadisiliwangi.core.utils.Constant.FULFILLMENT
import id.rifqipadisiliwangi.core.utils.Constant.HISTORY
import id.rifqipadisiliwangi.core.utils.Constant.LOGIN
import id.rifqipadisiliwangi.core.utils.Constant.PROFILE
import id.rifqipadisiliwangi.core.utils.Constant.RATING
import id.rifqipadisiliwangi.core.utils.Constant.REFRESH
import id.rifqipadisiliwangi.core.utils.Constant.REGISTER
import id.rifqipadisiliwangi.core.utils.Constant.REVIEW
import id.rifqipadisiliwangi.core.utils.Constant.SEARCH_SUGGESTION
import id.rifqipadisiliwangi.core.utils.Constant.STORE
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApplicationService {

    @POST(LOGIN)
    suspend fun userLogin(@Body userLoginRequest: RequestLoginItem): ResponseLoginItem

    @POST(REGISTER)
    suspend fun userRegister(@Body userRegisterRequest: RequestRegisterItem): ResponseRegisterItem

    @Multipart
    @POST(PROFILE)
    suspend fun userRegisterProfile(
        @Part("userName") userName: RequestBody?,
        @Part userImage: MultipartBody.Part?
    ): ResponseRegisterProfile

    @POST(STORE)
    suspend fun productStore(
        @Query("search") searchItem:String? = null,
        @Query("brand") brandItem:String? = null,
        @Query("lowest") lowestItem:Int? = null,
        @Query("highest") highestItem:Int? = null,
        @Query("sort") sortItem:String? = null,
        @Query("limit") limitItem:Int? = null,
        @Query("page") pageItem:Int? = null,
    ): ResponseStore
    @POST(SEARCH_SUGGESTION)
    suspend fun searchSuggestion(@Query("query") query: String? = null) : ResponseSearch

    @POST(REFRESH)
    suspend fun fetchRefreshToken(@Body refreshToken : RequestRefreshToken) : ResponseRefreshToken
    @GET(DETAIL)
    suspend fun fetchDetailStore(@Path("id") id: String? = null) : ResponseDetailStore
    @GET(REVIEW)
    suspend fun fetchReviewProduct(@Path("id") id: String? = null) : ResponseReviewItem
    @POST(FULFILLMENT)
    suspend fun postFullFillment(@Body body : RequestFullfilment) : ResponseFulfillment
    @GET(HISTORY)
    suspend fun getHistory() : ResponseHistory

    @POST(RATING)
    suspend fun userRating(@Body userRating: RequestRating): ResponseRating


    companion object{
        @JvmStatic
        operator fun invoke(
            chucker: ChuckerInterceptor,
            authInterceptor: AuthInterceptor,
            sessionInterceptor: SessionInterceptor,
            tokenInterceptor: TokenInterceptor,
        ): ApplicationService {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(chucker)
                .addInterceptor(authInterceptor)
                .addInterceptor(sessionInterceptor)
                .authenticator(tokenInterceptor)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(ApplicationService::class.java)
        }

    }
}