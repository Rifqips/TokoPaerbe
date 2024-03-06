package id.rifqipadisiliwangi.core.domain.model.fulfillment

import androidx.annotation.Keep
import id.rifqipadisiliwangi.core.data.network.api.model.fulfillment.FulfillmentItem


@Keep
data class FulfillmentRequest(
    val payment: String,
    val items: List<FulfillmentItem>
)

@Keep
data class ItemFulfillment(
    val productId: String,
    val variantName: String,
    val quantity: Int
)