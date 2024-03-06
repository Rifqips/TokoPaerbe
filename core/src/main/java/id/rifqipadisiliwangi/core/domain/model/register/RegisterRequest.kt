package id.rifqipadisiliwangi.core.domain.model.register

data class RegisterRequest(
    val email: String? ="",
    val password: String? ="",
    val firebaseToken: String? ="",
)
