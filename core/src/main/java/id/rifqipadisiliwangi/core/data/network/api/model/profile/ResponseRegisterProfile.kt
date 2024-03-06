package id.rifqipadisiliwangi.core.data.network.api.model.profile

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ResponseRegisterProfile(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: DataResponseRegisterProfile,
)

@Keep
data class DataResponseRegisterProfile(
    @SerializedName("userName")
    val userName: String?,
    @SerializedName("userImage")
    val userImage: String?,
)

