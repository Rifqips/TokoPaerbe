package id.rifqipadisiliwangi.core.data.network.api.model.refresh


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ResponseRefreshToken(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: DataRefreshToken,
    @SerializedName("message")
    val message: String
) : Parcelable {
    @Keep
    @Parcelize
    data class DataRefreshToken(
        @SerializedName("accessToken")
        val accessToken: String,
        @SerializedName("expiresAt")
        val expiresAt: Int,
        @SerializedName("refreshToken")
        val refreshToken: String
    ) : Parcelable
}