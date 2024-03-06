package id.rifqipadisiliwangi.core.domain.model.rating

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class RatingRequest(
    val invoiceId: String?,
    val rating: String?,
    val review: String?
) : Parcelable