package id.rifqipadisiliwangi.core.data.network.api.model.rating


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ResponseRating(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String
) : Parcelable