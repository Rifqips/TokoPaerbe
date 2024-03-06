package id.rifqipadisiliwangi.core.data.network.api.model.store


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ResponseStore(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val data: DataResult,
    @SerializedName("message")
    val message: String
) : Parcelable
    @Keep
    @Parcelize
    data class DataResult(
        @SerializedName("currentItemCount")
        val currentItemCount: Int,
        @SerializedName("items")
        val items: List<ResponseItemDataStore>,
        @SerializedName("itemsPerPage")
        val itemsPerPage: Int,
        @SerializedName("pageIndex")
        val pageIndex: Int,
        @SerializedName("totalPages")
        val totalPages: Int
    ) : Parcelable

    @Keep
    @Parcelize
    data class ResponseItemDataStore(
        @SerializedName("brand")
        val brand: String? = null,
        @SerializedName("image")
        val image: String? = null,
        @SerializedName("productId")
        val productId: String? = null,
        @SerializedName("productName")
        val productName: String? = null,
        @SerializedName("productPrice")
        val productPrice: Int? = null,
        @SerializedName("productRating")
        val productRating: Double? = null,
        @SerializedName("sale")
        val sale: Int? = null,
        @SerializedName("store")
        val store: String? = null
    ) : Parcelable
