package id.rifqipadisiliwangi.core.domain.repository

import id.rifqipadisiliwangi.core.data.local.datastore.datasource.AppPreferenceDataSource
import id.rifqipadisiliwangi.core.data.network.api.datasource.UserDataSource
import id.rifqipadisiliwangi.core.data.network.api.model.login.RequestLoginItem
import id.rifqipadisiliwangi.core.data.network.api.model.refresh.RequestRefreshToken
import id.rifqipadisiliwangi.core.data.network.api.model.register.RequestRegisterItem
import id.rifqipadisiliwangi.core.domain.model.login.LoginRequest
import id.rifqipadisiliwangi.core.domain.model.login.LoginResponse
import id.rifqipadisiliwangi.core.domain.model.profile.RequestRegisterProfileItem
import id.rifqipadisiliwangi.core.domain.model.refresh.TokenRefreshRequest
import id.rifqipadisiliwangi.core.domain.model.refresh.TokenRefreshResponse
import id.rifqipadisiliwangi.core.domain.model.register.RegisterRequest
import id.rifqipadisiliwangi.core.domain.model.register.RegisterResponseItem
import id.rifqipadisiliwangi.core.utils.DataMapper.toLoginResponse
import id.rifqipadisiliwangi.core.utils.DataMapper.toRefreshToken
import id.rifqipadisiliwangi.core.utils.DataMapper.toRegisterProfileResponse
import id.rifqipadisiliwangi.core.utils.DataMapper.toRegisterResponse
import id.rifqipadisiliwangi.core.utils.ResultWrapper
import id.rifqipadisiliwangi.core.utils.proceedFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface AuthRepository {

    suspend fun userLogin(request: LoginRequest): Flow<ResultWrapper<LoginResponse>>
    suspend fun fetchRefreshToken(requestToken: TokenRefreshRequest): Flow<ResultWrapper<TokenRefreshResponse>>
    suspend fun userRegister(request: RegisterRequest): Flow<ResultWrapper<RegisterResponseItem>>
    suspend fun userRegisterProfile(userName : RequestBody?, userImage : MultipartBody.Part?): Flow<ResultWrapper<RequestRegisterProfileItem>>
    suspend fun getUserToken(): String
    suspend fun saveUserToken(token: String)
    suspend fun getRefreshToken(): String
    suspend fun saveRefreshToken(token: String)
    fun getStateOnboarding(): Flow<Boolean>
    suspend fun saveStateOnboarding(isActive: Boolean)
    fun getStateDarkmode(): Flow<Boolean>
    suspend fun saveStateDarkmode(isActive: Boolean)
    fun getStateLocale(): Flow<Boolean>
    suspend fun saveStateLocale(isActive: Boolean)
    suspend fun saveFirebaseToken(firebaseToken: String)
    suspend fun getFirebaseToken(): String
    suspend fun saveUsername(username: String)
    suspend fun getUsername(): String
    suspend fun saveUserId(id: String)
    suspend fun getUserId(): String
    suspend fun deleteData()

}

class AuthRepositoryImpl(
    private val userDataSource: UserDataSource,
    private val userPreferenceDataSource: AppPreferenceDataSource
) : AuthRepository {
    override suspend fun userLogin(request: LoginRequest): Flow<ResultWrapper<LoginResponse>> {
        return proceedFlow {
            val dataReq = RequestLoginItem(request.email, request.password, request.firebaseToken,)
            val loginResult = userDataSource.userLogin(dataReq)
            userPreferenceDataSource.saveUserToken(loginResult.data.accessToken)
            userPreferenceDataSource.saveRefreshToken(loginResult.data.refreshToken)
            loginResult.toLoginResponse()
        }
    }

    override suspend fun fetchRefreshToken(requestToken: TokenRefreshRequest): Flow<ResultWrapper<TokenRefreshResponse>> {
        return proceedFlow {
            val dataReq = RequestRefreshToken(requestToken.token)
            val refreshResult = userDataSource.refreshToken(dataReq)
            userPreferenceDataSource.saveUserToken(refreshResult.data.accessToken)
            userPreferenceDataSource.saveRefreshToken(refreshResult.data.refreshToken)
            refreshResult.toRefreshToken()
        }
    }

    override suspend fun userRegister(request: RegisterRequest): Flow<ResultWrapper<RegisterResponseItem>> {
        return proceedFlow {
            val dataReq = RequestRegisterItem(request.email, request.password, request.firebaseToken)
            val registerResult = userDataSource.userRegister(dataReq)
            registerResult.data?.let { userPreferenceDataSource.saveUserToken(it.accessToken) }
            registerResult.data?.let { userPreferenceDataSource.saveRefreshToken(it.refreshToken) }
            registerResult.toRegisterResponse()
        }
    }

    override suspend fun userRegisterProfile(
        userName: RequestBody?,
        userImage: MultipartBody.Part?
    ): Flow<ResultWrapper<RequestRegisterProfileItem>> {
        return proceedFlow {
            userDataSource.userRegisterProfile(
                userName,
                userImage
            ).data.toRegisterProfileResponse()
        }.catch {
            emit(ResultWrapper.Error(Exception(it)))
        }.onStart {
            emit(ResultWrapper.Loading())
            delay(2000)
        }
    }

    override suspend fun getUserToken(): String {
        return userPreferenceDataSource.getUserToken()
    }

    override suspend fun saveUserToken(token: String) {
        return userPreferenceDataSource.saveUserToken(token)
    }

    override suspend fun getRefreshToken(): String {
        return userPreferenceDataSource.getRefreshToken()
    }

    override suspend fun saveRefreshToken(token: String) {
        return userPreferenceDataSource.saveRefreshToken(token)
    }

    override fun getStateOnboarding(): Flow<Boolean> {
        return userPreferenceDataSource.getStateOnboarding()
    }

    override suspend fun saveStateOnboarding(isActive: Boolean) {
        return userPreferenceDataSource.saveStateOnboarding(isActive)
    }

    override fun getStateDarkmode(): Flow<Boolean> {
        return userPreferenceDataSource.getStateDarkmode()
    }

    override suspend fun saveStateDarkmode(isActive: Boolean) {
        return userPreferenceDataSource.saveStateDarkmode(isActive)
    }

    override fun getStateLocale(): Flow<Boolean> {
        return userPreferenceDataSource.getStateLocale()
    }

    override suspend fun saveStateLocale(isActive: Boolean) {
        return userPreferenceDataSource.saveStateLocale(isActive)
    }

    override suspend fun saveFirebaseToken(firebaseToken: String) {
        return userPreferenceDataSource.saveFirebaseToken(firebaseToken)
    }

    override suspend fun getFirebaseToken(): String {
        return userPreferenceDataSource.getFirebaseToken()
    }

    override suspend fun saveUsername(username: String) {
        return userPreferenceDataSource.saveUsername(username)
    }

    override suspend fun getUsername(): String {
        return userPreferenceDataSource.getUsername()
    }

    override suspend fun saveUserId(id: String) {
        return userPreferenceDataSource.saveUserId(id)
    }

    override suspend fun getUserId(): String {
        return userPreferenceDataSource.getUserId()
    }

    override suspend fun deleteData() {
        return userPreferenceDataSource.deleteData()
    }
}