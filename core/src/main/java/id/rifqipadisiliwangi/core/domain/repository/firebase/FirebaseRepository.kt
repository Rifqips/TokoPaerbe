package id.rifqipadisiliwangi.core.domain.repository.firebase

import android.content.Context
import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.google.gson.Gson
import id.rifqipadisiliwangi.core.R
import id.rifqipadisiliwangi.core.domain.model.payment.PaymentResponse

interface FirebaseRepository {

    fun getPaymentConfig(callback: (PaymentResponse) -> Unit)
    fun getUpdatePaymentConfig(callback: (PaymentResponse) -> Unit)


}

class FirebaseRepositoryImpl(
    private val context: Context,
) : FirebaseRepository{

    private val remoteConfig: FirebaseRemoteConfig by lazy {
        Firebase.remoteConfig
    }

    init {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_default)
    }

    override fun getPaymentConfig(callback: (PaymentResponse) -> Unit) {
        remoteConfig.fetchAndActivate().addOnCompleteListener() { task ->
            if (task.isSuccessful) {
                val gson = Gson()
                val stringJson = remoteConfig.getString("Payment")
                Log.d("remoteConfig", stringJson.toString())
                if (stringJson.isNotEmpty()) {
                    val jsonModel = gson.fromJson(stringJson, PaymentResponse::class.java)
                    callback(jsonModel)
                }
            }
        }
    }

    override fun getUpdatePaymentConfig(callback: (PaymentResponse) -> Unit) {
        remoteConfig.addOnConfigUpdateListener(object : ConfigUpdateListener {
            override fun onUpdate(configUpdate: ConfigUpdate) {
                if (configUpdate.updatedKeys.contains("Payment")) {
                    remoteConfig.activate().addOnCompleteListener {
                        val gson = Gson()
                        val stringJson = remoteConfig.getString("Payment")
                        val jsonModel = gson.fromJson(stringJson, PaymentResponse::class.java)
                        callback(jsonModel)
                    }
                }
            }

            override fun onError(error: FirebaseRemoteConfigException) {
            }
        })
    }


}