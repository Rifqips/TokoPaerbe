package id.rifqipadisiliwangi.tokopaerbe.utils.exceptions

import com.google.gson.Gson
import id.rifqipadisiliwangi.core.data.network.api.model.login.ResponseLoginItem
import id.rifqipadisiliwangi.core.data.network.api.model.profile.ResponseRegisterProfile
import id.rifqipadisiliwangi.core.data.network.api.model.register.ResponseRegisterItem
import retrofit2.Response


class ApiException(
    override val message: String?,
    val httpCode: Int,
    val errorResponse: Response<*>?
) : Exception() {

    fun getParsedErrorLogin(): ResponseLoginItem? {
        val body = errorResponse?.errorBody()?.string().orEmpty()
        return try {
            val bodyObj = Gson().fromJson(body, ResponseLoginItem::class.java)
            bodyObj
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getParsedErrorResister(): ResponseRegisterItem? {
        val body = errorResponse?.errorBody()?.string().orEmpty()
        return try {
            val bodyObj = Gson().fromJson(body, ResponseRegisterItem::class.java)
            bodyObj
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getParsedErrorResisterProfile(): ResponseRegisterProfile? {
        val body = errorResponse?.errorBody()?.string().orEmpty()
        return try {
            val bodyObj = Gson().fromJson(body, ResponseRegisterProfile::class.java)
            bodyObj
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}