package id.rifqipadisiliwangi.core.domain.model.review

import android.os.Parcelable
import androidx.annotation.Keep
import id.rifqipadisiliwangi.core.data.network.api.model.review.DataResponseReviewItem
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
data class ItemReviewResponse(
    val code: Int,
    val data: List<DataResponseReviewItem>,
    val message: String
) : Parcelable

@Keep
@Parcelize
data class DataResponseReviewItemDataResponse(
    val userImage: String,
    val userName: String,
    val userRating: Int,
    val userReview: String
) : Parcelable