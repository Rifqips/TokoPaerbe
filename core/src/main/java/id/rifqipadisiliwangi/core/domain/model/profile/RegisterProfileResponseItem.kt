package id.rifqipadisiliwangi.core.domain.model.profile

import id.rifqipadisiliwangi.core.data.network.api.model.profile.DataResponseRegisterProfile

data class RegisterProfileResponseItem(
    val code: Int,
    val data: DataResponseRegisterProfile,
    val message: String
)


