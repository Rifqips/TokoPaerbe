package id.rifqipadisiliwangi.core.domain.model.refresh

import androidx.annotation.Keep
import id.rifqipadisiliwangi.core.data.network.api.model.refresh.ResponseRefreshToken

@Keep
data class TokenRefreshResponse (
    val code: Int,
    val `data`: ResponseRefreshToken.DataRefreshToken,
    val message: String
)