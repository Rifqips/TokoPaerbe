package id.rifqipadisiliwangi.core.domain.repository.firebase

import com.google.firebase.crashlytics.FirebaseCrashlytics

interface FirebaseLogger {
    fun logExeception(exception: Exception)

}

class FirebaseCrashlyticsLoggerImpl : FirebaseLogger {
    override fun logExeception(exception: Exception) {
        FirebaseCrashlytics.getInstance().recordException(exception)
    }
}
