package id.rifqipadisiliwangi.core.domain.model.history

import android.os.Parcelable
import androidx.annotation.Keep
import id.rifqipadisiliwangi.core.data.network.api.model.history.HistoryData
import id.rifqipadisiliwangi.core.data.network.api.model.history.HistoryItem
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
data class HistoryResponse(
    val code: Int,
    val data: List<HistoryData>,
    val message: String
): Parcelable


@Keep
@Parcelize
data class DataHistory(
    val date: String,
    val image: String,
    val invoiceId: String,
    val items: List<ItemHistory>,
    val name: String,
    val payment: String,
    val rating: Int,
    val review: String,
    val status: String,
    val time: String,
    val total: Int
) : Parcelable

@Keep
@Parcelize
data class ItemHistory(
    val productId: String,
    val quantity: Int,
    val variantName: String
) : Parcelable


fun HistoryItem.toItemsHistoryMapper() = ItemHistory(
    productId = this.productId,
    quantity = this.quantity,
    variantName = this.variantName
)

fun Collection<HistoryItem>.toItemsHistoryList() = this.map { it.toItemsHistoryMapper() }

