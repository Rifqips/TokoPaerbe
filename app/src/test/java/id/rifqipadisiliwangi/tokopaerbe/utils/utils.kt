package id.rifqipadisiliwangi.tokopaerbe.utils

import id.rifqipadisiliwangi.core.data.network.api.model.login.DataResponseLoginItem
import id.rifqipadisiliwangi.core.data.network.api.model.register.DataResponseRegisterItem
import id.rifqipadisiliwangi.core.domain.model.login.LoginResponse
import id.rifqipadisiliwangi.core.domain.model.profile.RequestRegisterProfileItem
import id.rifqipadisiliwangi.core.domain.model.register.RegisterResponseItem
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

object utils {

    val login = LoginResponse(
        code = 200,
        data = DataResponseLoginItem(
            userImage = "",
            userName = "",
            accessToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJlY29tbWVyY2UtYXVkaWVuY2UiLCJpc3MiOiJodHRwOi8vMTkyLjE2OC4xNTMuMTI1OjUwMDAvIiwidXNlcklkIjoiODM0Yjk4YWQtZDcwYi00NGNhLTg4ZDMtZTU3NzFlNDNhYjU3IiwidG9rZW5UeXBlIjoiYWNjZXNzVG9rZW4iLCJleHAiOjE2OTgxMjM1MTV9.PRLiDsyvP00xWyWRUP4ySqL4hsiRp4Twelvwx8HsWQT7guasEXTRaMOb8f9gYEGflTPOuW_MDqYHzlSkF9Y8Mw",
            expiresAt = 600,
            refreshToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJlY29tbWVyY2UtYXVkaWVuY2UiLCJpc3MiOiJodHRwOi8vMTkyLjE2OC4xNTMuMTI1OjUwMDAvIiwidXNlcklkIjoiODM0Yjk4YWQtZDcwYi00NGNhLTg4ZDMtZTU3NzFlNDNhYjU3IiwidG9rZW5UeXBlIjoiYWNjZXNzVG9rZW4iLCJleHAiOjE2OTgxMjM1MTV9.PRLiDsyvP00xWyWRUP4ySqL4hsiRp4Twelvwx8HsWQT7guasEXTRaMOb8f9gYEGflTPOuW_MDqYHzlSkF9Y8Mw",
        ),
        message = "OK"

    )

    val register = RegisterResponseItem(
        code = 200,
        data = DataResponseRegisterItem(
            accessToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJlY29tbWVyY2UtYXVkaWVuY2UiLCJpc3MiOiJodHRwOi8vMTkyLjE2OC4xNTMuMTI1OjUwMDAvIiwidXNlcklkIjoiODM0Yjk4YWQtZDcwYi00NGNhLTg4ZDMtZTU3NzFlNDNhYjU3IiwidG9rZW5UeXBlIjoiYWNjZXNzVG9rZW4iLCJleHAiOjE2OTgxMjM1MTV9.PRLiDsyvP00xWyWRUP4ySqL4hsiRp4Twelvwx8HsWQT7guasEXTRaMOb8f9gYEGflTPOuW_MDqYHzlSkF9Y8Mw",
            expiresAt = 600,
            refreshToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJlY29tbWVyY2UtYXVkaWVuY2UiLCJpc3MiOiJodHRwOi8vMTkyLjE2OC4xNTMuMTI1OjUwMDAvIiwidXNlcklkIjoiODM0Yjk4YWQtZDcwYi00NGNhLTg4ZDMtZTU3NzFlNDNhYjU3IiwidG9rZW5UeXBlIjoiYWNjZXNzVG9rZW4iLCJleHAiOjE2OTgxMjM1MTV9.PRLiDsyvP00xWyWRUP4ySqL4hsiRp4Twelvwx8HsWQT7guasEXTRaMOb8f9gYEGflTPOuW_MDqYHzlSkF9Y8Mw",
        ),
        message = "OK"

    )
    val profile = RequestRegisterProfileItem(
        userName = "",
        userImage = ""
    )


    val imageFile : File? = null
    val imageRequestBody = imageFile?.asRequestBody("multipart/form-data".toMediaTypeOrNull())
    val imageMultipart: MultipartBody.Part? = imageRequestBody?.let {
        MultipartBody.Part.createFormData("userImage", imageFile?.name, it )
    }
}