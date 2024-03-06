package id.rifqipadisiliwangi.core.domain.repository.firebase

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

interface FirebaseDebugView {

    fun debugSreenView(debug : String)

    fun logEvent(eventName : String, bundle : Bundle)
}

class FirebaseDebugViewImpl(
    private val firebaseAnalytics: FirebaseAnalytics
) : FirebaseDebugView {
    override fun debugSreenView(debug: String) {
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, Bundle().apply { putString("screen_view",debug) })
    }

    override fun logEvent(eventName: String, bundle: Bundle) {
        firebaseAnalytics.logEvent(eventName, bundle)
    }
}