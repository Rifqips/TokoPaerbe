package id.rifqipadisiliwangi.core.domain.usecase

import android.os.Bundle
import id.rifqipadisiliwangi.core.domain.model.login.LoginRequest
import id.rifqipadisiliwangi.core.domain.model.login.LoginResponse
import id.rifqipadisiliwangi.core.domain.model.profile.RequestRegisterProfileItem
import id.rifqipadisiliwangi.core.domain.model.refresh.TokenRefreshRequest
import id.rifqipadisiliwangi.core.domain.model.refresh.TokenRefreshResponse
import id.rifqipadisiliwangi.core.domain.model.register.RegisterRequest
import id.rifqipadisiliwangi.core.domain.model.register.RegisterResponseItem
import id.rifqipadisiliwangi.core.domain.repository.AuthRepository
import id.rifqipadisiliwangi.core.domain.repository.firebase.FirebaseDebugView
import id.rifqipadisiliwangi.core.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface AuthUseCase {
    suspend fun fetchLogin(request : LoginRequest) : Flow<ResultWrapper<LoginResponse>>
    suspend fun fetchRefreshToken(request : TokenRefreshRequest) : Flow<ResultWrapper<TokenRefreshResponse>>
    suspend fun fetchRegister(request: RegisterRequest) : Flow<ResultWrapper<RegisterResponseItem>>
    suspend fun fetchRegisterProfile(userName : RequestBody?, userImage : MultipartBody.Part?) : Flow<ResultWrapper<RequestRegisterProfileItem>>
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

    fun debugSreenView(debug : String)

    fun logEvent(eventName : String, bundle : Bundle)


}

class AuthInteractor(
    private val authRepository: AuthRepository,
    private val analytics: FirebaseDebugView
) : AuthUseCase {

    override suspend fun fetchLogin(request: LoginRequest): Flow<ResultWrapper<LoginResponse>> {
        return authRepository.userLogin(request)
    }

    override suspend fun fetchRefreshToken(refreshToken: TokenRefreshRequest): Flow<ResultWrapper<TokenRefreshResponse>> {
        return authRepository.fetchRefreshToken(refreshToken)
    }

    override suspend fun fetchRegister(request: RegisterRequest): Flow<ResultWrapper<RegisterResponseItem>> {
        return authRepository.userRegister(request)
    }

    override suspend fun fetchRegisterProfile(
        userName: RequestBody?,
        userImage: MultipartBody.Part?
    ): Flow<ResultWrapper<RequestRegisterProfileItem>> {
        return authRepository.userRegisterProfile(userName, userImage)
    }

    override suspend fun getUserToken(): String {
        return authRepository.getUserToken()
    }

    override suspend fun saveUserToken(token: String) {
        return authRepository.saveUserToken(token)
    }

    override suspend fun getRefreshToken(): String {
        return authRepository.getRefreshToken()
    }

    override suspend fun saveRefreshToken(token: String) {
        return authRepository.saveRefreshToken(token)
    }

    override fun getStateOnboarding(): Flow<Boolean> {
        return authRepository.getStateOnboarding()
    }

    override suspend fun saveStateOnboarding(isActive: Boolean) {
        return authRepository.saveStateOnboarding(isActive)
    }

    override fun getStateDarkmode(): Flow<Boolean> {
        return authRepository.getStateDarkmode()
    }

    override suspend fun saveStateDarkmode(isActive: Boolean) {
        return authRepository.saveStateDarkmode(isActive)
    }

    override fun getStateLocale(): Flow<Boolean> {
        return authRepository.getStateLocale()
    }

    override suspend fun saveStateLocale(isActive: Boolean) {
        return authRepository.saveStateLocale(isActive)
    }

    override suspend fun saveFirebaseToken(firebaseToken: String) {
        return authRepository.saveFirebaseToken(firebaseToken)
    }

    override suspend fun getFirebaseToken(): String {
        return authRepository.getFirebaseToken()
    }

    override suspend fun saveUsername(username: String) {
        return authRepository.saveUsername(username)
    }

    override suspend fun getUsername(): String {
        return authRepository.getUsername()
    }

    override suspend fun saveUserId(id: String) {
        return authRepository.saveUserId(id)
    }

    override suspend fun getUserId(): String {
        return authRepository.getUserId()
    }

    override suspend fun deleteData() {
        return authRepository.deleteData()
    }

    override fun debugSreenView(debug: String) {
        analytics.debugSreenView(debug)
    }

    override fun logEvent(eventName: String, bundle: Bundle) {
        analytics.logEvent(eventName, bundle)
    }
}