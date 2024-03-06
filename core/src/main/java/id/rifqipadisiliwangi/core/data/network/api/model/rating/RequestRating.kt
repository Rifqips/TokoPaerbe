package id.rifqipadisiliwangi.core.data.network.api.model.rating


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class RequestRating(
    @SerializedName("invoiceId")
    val invoiceId: String?,
    @SerializedName("rating")
    val rating: String?,
    @SerializedName("review")
    val review: String?
) : Parcelable