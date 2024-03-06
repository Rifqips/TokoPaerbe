package id.rifqipadisiliwangi.core.domain.model.register

import id.rifqipadisiliwangi.core.data.network.api.model.register.DataResponseRegisterItem

data class RegisterResponseItem(
    val code: Int = 0,
    val data: DataResponseRegisterItem? = DataResponseRegisterItem(),
    val message: String = ""
)

