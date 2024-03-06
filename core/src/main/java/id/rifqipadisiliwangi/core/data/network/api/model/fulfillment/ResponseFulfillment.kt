package id.rifqipadisiliwangi.core.data.network.api.model.fulfillment


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import id.rifqipadisiliwangi.core.domain.model.fulfillment.FulfillmentResponse
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ResponseFulfillment(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val data: FulfillmentData,
    @SerializedName("message")
    val message: String
) : Parcelable
@Keep
@Parcelize
data class FulfillmentData(
    @SerializedName("date")
    val date: String,
    @SerializedName("invoiceId")
    val invoiceId: String,
    @SerializedName("payment")
    val payment: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("time")
    val time: String,
    @SerializedName("total")
    val total: Int
) : Parcelable

fun ResponseFulfillment.toResponseFulfillment() = FulfillmentResponse(
    code = this.code,
    data = this.data,
    message = this.message
)