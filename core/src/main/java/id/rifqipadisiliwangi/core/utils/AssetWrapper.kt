package id.rifqipadisiliwangi.core.utils

import android.content.Context
import androidx.annotation.StringRes

class AssetWrapper(private val appContext: Context) {
    fun getString(@StringRes id: Int): String {
        return appContext.getString(id)
    }
}