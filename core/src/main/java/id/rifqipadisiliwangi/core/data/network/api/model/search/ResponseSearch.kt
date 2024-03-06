package id.rifqipadisiliwangi.core.data.network.api.model.search


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ResponseSearch(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val data: List<String>,
    @SerializedName("message")
    val message: String
) : Parcelable