package id.rifqipadisiliwangi.core.data.network.api.model.register

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class RequestRegisterItem(
    @SerializedName("email")
    val email: String? = "",
    @SerializedName("password")
    val password: String? = "",
    @SerializedName("firebaseToken")
    val firebaseToken: String? = ""
) : Parcelable
