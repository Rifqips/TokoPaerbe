package id.rifqipadisiliwangi.core.domain.model.login

data class LoginRequest(
    val email: String? = null,
    val password: String? = null,
    val firebaseToken: String? = null
)