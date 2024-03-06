package id.rifqipadisiliwangi.core.data.network.api.model.fulfillment


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class RequestFullfilment(
    @SerializedName("payment")
    val payment: String,
    @SerializedName("items")
    val items: List<FulfillmentItem>
) : Parcelable
@Keep
@Parcelize
data class FulfillmentItem(
    @SerializedName("productId")
    val productId: String,
    @SerializedName("variantName")
    val variantName: String,
    @SerializedName("quantity")
    val quantity: Int
) : Parcelable

