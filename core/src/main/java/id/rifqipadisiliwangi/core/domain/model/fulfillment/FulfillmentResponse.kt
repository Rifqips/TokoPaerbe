package id.rifqipadisiliwangi.core.domain.model.fulfillment

import android.os.Parcelable
import androidx.annotation.Keep
import id.rifqipadisiliwangi.core.data.network.api.model.fulfillment.FulfillmentData
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
data class FulfillmentResponse(
    val code: Int,
    val `data`: FulfillmentData,
    val message: String
) : Parcelable

@Keep
@Parcelize
data class DataFulfillment(
    val date: String,
    val invoiceId: String,
    val payment: String,
    val status: Boolean,
    val time: String,
    val total: Int
) : Parcelable