package id.rifqipadisiliwangi.core.domain.model.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RequestRegisterProfileItem(
    val userName: String? = "",
    val userImage: String? = ""
) : Parcelable
