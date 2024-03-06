package id.rifqipadisiliwangi.core.data.network.api.model.history


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ResponseHistory(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val data: List<HistoryData>,
    @SerializedName("message")
    val message: String
) : Parcelable
@Keep
@Parcelize
data class HistoryData(
    @SerializedName("date")
    val date: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("invoiceId")
    val invoiceId: String,
    @SerializedName("items")
    val items: List<HistoryItem>,
    @SerializedName("name")
    val name: String,
    @SerializedName("payment")
    val payment: String,
    @SerializedName("rating")
    val rating: Int?,
    @SerializedName("review")
    val review: String?,
    @SerializedName("status")
    val status: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("total")
    val total: Int
) : Parcelable
@Keep
@Parcelize
data class HistoryItem(
    @SerializedName("productId")
    val productId: String,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("variantName")
    val variantName: String
) : Parcelable


