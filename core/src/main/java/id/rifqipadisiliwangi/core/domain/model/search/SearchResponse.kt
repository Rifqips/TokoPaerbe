package id.rifqipadisiliwangi.core.domain.model.search


import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class SearchResponse(
    val code: Int,
    val data: List<String>,
    val message: String
) : Parcelable