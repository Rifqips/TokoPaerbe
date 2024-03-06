package id.rifqipadisiliwangi.core.domain.model.detail

import android.os.Parcelable
import androidx.annotation.Keep
import id.rifqipadisiliwangi.core.data.network.api.model.detail.ResponseDetailStore
import id.rifqipadisiliwangi.core.data.network.api.model.detail.ResponseDetailStore.DataResponseDetail.ProductVariantResponseDetail
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class StoreDetailResponse(
    val code: Int,
    val `data`: ResponseDetailStore.DataResponseDetail,
    val message: String
) : Parcelable

@Keep
@Parcelize
data class StoreDetailData(
    val brand: String,
    val description: String,
    val image: List<String>,
    val productId: String,
    val productName: String,
    var productPrice: Int,
    val productRating: Double,
    val productVariant: List<StoreDetailProductVariant>,
    val sale: Int,
    val stock: Int,
    val store: String,
    val totalRating: Int,
    val totalReview: Int,
    val totalSatisfaction: Int
) : Parcelable

@Keep
@Parcelize
data class StoreDetailProductVariant(
    val variantName: String,
    val variantPrice: Int
) : Parcelable

fun ProductVariantResponseDetail.toProductVarian() = StoreDetailProductVariant(
    variantName = this.variantName,
    variantPrice = this.variantPrice
)
fun Collection<ProductVariantResponseDetail>.toProductVarianList() = this.map { it.toProductVarian() }

