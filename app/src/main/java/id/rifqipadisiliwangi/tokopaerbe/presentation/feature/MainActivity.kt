package id.rifqipadisiliwangi.tokopaerbe.presentation.feature

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.LocaleListCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import id.rifqipadisiliwangi.tokopaerbe.R
import id.rifqipadisiliwangi.tokopaerbe.databinding.ActivityMainBinding
import id.rifqipadisiliwangi.tokopaerbe.presentation.feature.dashboard.DashboardViewModel
import id.rifqipadisiliwangi.tokopaerbe.utils.Constant.en
import id.rifqipadisiliwangi.tokopaerbe.utils.Constant.idn
import id.rifqipadisiliwangi.tokopaerbe.utils.IOnBackPressed
import id.rifqipadisiliwangi.tokopaerbe.utils.LocalizationUtil
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val vmDashboard: DashboardViewModel by viewModel()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setDarkMode()
        setUpLocalization()
        fetchFcm()

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
    }

    private fun fetchFcm() {
        Firebase.messaging.subscribeToTopic("promo")
            .addOnCompleteListener { task ->
                var msg = "Subscribe Success"
                if (!task.isSuccessful) {
                    msg = "Subscribe Failed"
                }
            }
    }

    private fun setUpLocalization() {
        vmDashboard.appLocaleLiveData.observe(this) { isActive ->
            when (isActive) {
                true -> {
                    LocalizationUtil.setLocale(this, idn)
                    val appLocale: LocaleListCompat =
                        LocaleListCompat.forLanguageTags(idn)
                    AppCompatDelegate.setApplicationLocales(appLocale)
                }

                else -> {
                    LocalizationUtil.setLocale(this, en)
                    val appLocale: LocaleListCompat =
                        LocaleListCompat.forLanguageTags(en)
                    AppCompatDelegate.setApplicationLocales(appLocale)
                }
            }
        }
    }

    private fun setDarkMode() {
        vmDashboard.appThemeLiveData.observe(this) { isActive ->
            when (isActive) {
                true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    /**
     * Commentar still in use
     */

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        val fragment =
            this.supportFragmentManager.findFragmentById(R.id.container_navigation)
        (fragment as? IOnBackPressed)?.onBackPressed()?.not()?.let {
            super.onBackPressed()
        }
    }

    private fun showDialog() {
        MaterialAlertDialogBuilder(this).apply {
            setTitle(R.string.string_anda_yakin)
            setMessage(R.string.string_keluar_aplikasi)
            setPositiveButton(R.string.string_iya) { _, _ -> finish() }
            setNegativeButton(R.string.string_tidak, null)
            show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.POST_NOTIFICATIONS)
        private const val REQUEST_CODE_PERMISSIONS = 200
    }


}

