package id.rifqipadisiliwangi.core.data.network.api.model.review


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ResponseReviewItem(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val data: List<DataResponseReviewItem>,
    @SerializedName("message")
    val message: String
) : Parcelable

@Keep
@Parcelize
data class DataResponseReviewItem(
    @SerializedName("userImage")
    val userImage: String,
    @SerializedName("userName")
    val userName: String,
    @SerializedName("userRating")
    val userRating: Int,
    @SerializedName("userReview")
    val userReview: String
) : Parcelable