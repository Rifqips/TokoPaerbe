package id.rifqipadisiliwangi.core.data.network.api.model.refresh


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class RequestRefreshToken(
    @SerializedName("token")
    val token: String
) : Parcelable