package id.rifqipadisiliwangi.core.domain.model.rating

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class RatingResponse(
    val code: Int,
    val message: String
) : Parcelable