package id.rifqipadisiliwangi.core.data.network.api.datasource

import id.rifqipadisiliwangi.core.data.network.api.model.login.RequestLoginItem
import id.rifqipadisiliwangi.core.data.network.api.model.login.ResponseLoginItem
import id.rifqipadisiliwangi.core.data.network.api.model.profile.ResponseRegisterProfile
import id.rifqipadisiliwangi.core.data.network.api.model.refresh.RequestRefreshToken
import id.rifqipadisiliwangi.core.data.network.api.model.refresh.ResponseRefreshToken
import id.rifqipadisiliwangi.core.data.network.api.model.register.RequestRegisterItem
import id.rifqipadisiliwangi.core.data.network.api.model.register.ResponseRegisterItem
import id.rifqipadisiliwangi.core.data.network.api.service.ApplicationService
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface UserDataSource {
    suspend fun userLogin(userLoginRequest: RequestLoginItem): ResponseLoginItem
    suspend fun userRegister(userRegisterRequest: RequestRegisterItem): ResponseRegisterItem
    suspend fun userRegisterProfile(
        userName : RequestBody?,
        userImage : MultipartBody.Part?,
    ): ResponseRegisterProfile

    suspend fun refreshToken(refreshRequeest: RequestRefreshToken): ResponseRefreshToken
}

class UserDataSourceImpl(private val service: ApplicationService) : UserDataSource {
    override suspend fun userLogin(userLoginRequest: RequestLoginItem): ResponseLoginItem {
        return service.userLogin(userLoginRequest)
    }

    override suspend fun userRegister(userRegisterRequest: RequestRegisterItem): ResponseRegisterItem {
        return service.userRegister(userRegisterRequest)
    }

    override suspend fun userRegisterProfile(
        userName: RequestBody?,
        userImage: MultipartBody.Part?
    ): ResponseRegisterProfile {
        return service.userRegisterProfile(userName, userImage)
    }

    override suspend fun refreshToken(refreshRequeest: RequestRefreshToken): ResponseRefreshToken {
        return service.fetchRefreshToken(refreshRequeest)
    }

}