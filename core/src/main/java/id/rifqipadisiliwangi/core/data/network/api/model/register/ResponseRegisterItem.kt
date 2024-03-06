package id.rifqipadisiliwangi.core.data.network.api.model.register

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ResponseRegisterItem(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: DataResponseRegisterItem?,
    @SerializedName("message")
    val message: String
)

@Keep
data class DataResponseRegisterItem(
    @SerializedName("accessToken")
    val accessToken: String ="",
    @SerializedName("expiresAt")
    val expiresAt: Int = 0,
    @SerializedName("refreshToken")
    val refreshToken: String = ""
)

