package id.rifqipadisiliwangi.core.data.network.api.model.profile

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class RequestRegisterProfile(
    @SerializedName("userImage")
    val userImage: String?,
    @SerializedName("userName")
    val userName: String?,
)
