package id.rifqipadisiliwangi.core.data.network.api.model.login

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ResponseLoginItem(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val data: DataResponseLoginItem,
    @SerializedName("message")
    val message: String
)

@Keep
data class DataResponseLoginItem(
    @SerializedName("accessToken")
    val accessToken: String = "",
    @SerializedName("expiresAt")
    val expiresAt: Int= 0,
    @SerializedName("refreshToken")
    val refreshToken: String= "",
    @SerializedName("userImage")
    val userImage: String= "",
    @SerializedName("userName")
    val userName: String= ""
)
