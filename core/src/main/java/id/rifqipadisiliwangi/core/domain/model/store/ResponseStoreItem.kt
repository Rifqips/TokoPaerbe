package id.rifqipadisiliwangi.core.domain.model.store

import android.os.Parcelable
import id.rifqipadisiliwangi.core.data.network.api.model.store.DataResult
import id.rifqipadisiliwangi.core.data.network.api.model.store.ResponseItemDataStore
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseStoreItem(
    val code: Int,
    val `data`: DataResult,
    val message: String
) : Parcelable

@Parcelize
data class DataItem(
    val currentItemCount: Int,
    val items: List<ItemStoreDataResponse>,
    val itemsPerPage: Int,
    val pageIndex: Int,
    val totalPages: Int
) : Parcelable

@Parcelize
data class ItemStoreDataResponse(
    val brand: String? = null,
    val image: String? = null,
    val productId: String? = null,
    val productName: String? = null,
    val productPrice: Int? = null,
    val productRating: Double? = null,
    val sale: Int? = null,
    val store: String? = null
) : Parcelable


//fun DataResponseReviewItem.toReviewProductMapper() = DataResponseReviewItemDataResponse(
//    userImage = this.userImage,
//    userName = this.userName,
//    userRating = this.userRating,
//    userReview = this.userReview
//)
//
//fun Collection<DataResponseReviewItem>.toReviewList() = this.map { it.toReviewProductMapper() }

fun ResponseItemDataStore.toItemsStoreData() = ItemStoreDataResponse(
    brand = this.brand,
    image = this.image,
    productId = this.productId,
    productName = this.productName,
    productRating = this.productRating,
    sale = this.sale,
    store = this.store
)
fun Collection<ResponseItemDataStore>.toItemsStoreDataList() = this.map { it.toItemsStoreData() }

