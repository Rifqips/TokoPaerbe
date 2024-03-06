package id.rifqipadisiliwangi.core.data.network.api.model.detail


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ResponseDetailStore(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val data: DataResponseDetail,
    @SerializedName("message")
    val message: String
) : Parcelable {
    @Keep
    @Parcelize
    data class DataResponseDetail(
        @SerializedName("brand")
        val brand: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("image")
        val image: List<String>,
        @SerializedName("productId")
        val productId: String,
        @SerializedName("productName")
        val productName: String,
        @SerializedName("productPrice")
        val productPrice: Int,
        @SerializedName("productRating")
        val productRating: Double,
        @SerializedName("productVariant")
        val productVariant: List<ProductVariantResponseDetail>,
        @SerializedName("sale")
        val sale: Int,
        @SerializedName("stock")
        val stock: Int,
        @SerializedName("store")
        val store: String,
        @SerializedName("totalRating")
        val totalRating: Int,
        @SerializedName("totalReview")
        val totalReview: Int,
        @SerializedName("totalSatisfaction")
        val totalSatisfaction: Int
    ) : Parcelable {
        @Keep
        @Parcelize
        data class ProductVariantResponseDetail(
            @SerializedName("variantName")
            val variantName: String,
            @SerializedName("variantPrice")
            val variantPrice: Int
        ) : Parcelable


    }
}