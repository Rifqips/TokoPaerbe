package id.rifqipadisiliwangi.core.domain.model.login

import id.rifqipadisiliwangi.core.data.network.api.model.login.DataResponseLoginItem


data class LoginResponse (
    val code: Int = 0,
    val data: DataResponseLoginItem = DataResponseLoginItem(),
    val message: String = ""
)
