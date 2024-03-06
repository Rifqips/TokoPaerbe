package id.rifqipadisiliwangi.tokopaerbe.presentation.feature.register

import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.isGone
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import id.rifqipadisiliwangi.core.domain.model.register.RegisterRequest
import id.rifqipadisiliwangi.core.utils.proceedWhen
import id.rifqipadisiliwangi.tokopaerbe.R
import id.rifqipadisiliwangi.tokopaerbe.databinding.FragmentRegisterBinding
import id.rifqipadisiliwangi.tokopaerbe.utils.Constant.ig
import id.rifqipadisiliwangi.tokopaerbe.utils.Constant.yt
import id.rifqipadisiliwangi.tokopaerbe.utils.Helper.emailPattern
import id.rifqipadisiliwangi.tokopaerbe.utils.Helper.passwordPattern
import id.rifqipadisiliwangi.tokopaerbe.utils.Helper.toSpannableHyperLink
import id.rifqipadisiliwangi.tokopaerbe.utils.TextWatcherConfiguration
import id.rifqipadisiliwangi.tokopaerbe.utils.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment :
    BaseFragment<FragmentRegisterBinding, RegisterViewModel>(FragmentRegisterBinding::inflate){

    override val viewModel: RegisterViewModel by viewModel()
    private var firebaseToken = ""

    override fun initView() {
        setUpSpannable()
        fcmFetch()
        with(binding){
            menuToolbar.title = getString(R.string.string_daftar)
            etEmailInput.hint = getString(R.string.string_email)
            etEmailInput.helperText = getString(R.string.string_helper_email)
            etPasswordInput.helperText = getString(R.string.string_helper_password)
            etPasswordInput.hint = getString(R.string.string_password)
            btnRegister.text = getString(R.string.string_daftar)
            tvHelperRegister.text = getString(R.string.string_helper_register)
            btnLogin.text = getString(R.string.string_masuk)
        }
    }

    override fun initListener() {
        with(binding) {
            btnLogin.setOnClickListener {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
            btnRegister.setOnClickListener {
                doRegister()
                observeData()
            }
            etEmailEditRegister.addTextChangedListener(TextWatcherConfiguration(5) { email ->
                validateEmail(email)
            })
            etPasswordEditRegister.addTextChangedListener(TextWatcherConfiguration(5) { password ->
                validatePassword(password)
            })
        }
    }

    private fun observeData() {
        viewModel.registerResult.observe(viewLifecycleOwner){
            it.proceedWhen(
                doOnSuccess = {
                    binding.pbRegister.isGone = true
                    findNavController().navigate(R.id.action_registerFragment_to_registerProfileFragment)
                },
                doOnLoading = {
                    binding.pbRegister.isGone = false
                },
                doOnError = {
                    binding.pbRegister.isGone = true
                }
            )
        }
    }

    private fun fcmFetch(){
        Firebase.messaging.token.addOnCompleteListener(
            OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("cekTask", "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }
                val token = task.result
                firebaseToken = token
            },
        )
    }

    private fun analytics(data : String){
        val bundle = Bundle()
        bundle.putString("show_register_email", data)
        viewModel.logEventScreenView(FirebaseAnalytics.Event.SIGN_UP, bundle)
    }

    override fun onResume() {
        super.onResume()
        viewModel.debugScreenView(getString(R.string.string_daftar))
    }

    private fun doRegister() {
        //  if (isFormValid()) {
        //   }

        val email = binding.etEmailEditRegister.text.toString().trim()
        val password = binding.etPasswordEditRegister.text.toString().trim()
        val firebaseToken = ""
        analytics(email)

        val userAuth = RegisterRequest(
            email,
            password,
            firebaseToken,
        )
        viewModel.userRegister(userAuth)
    }

    private fun setUpSpannable(){
        with(binding){
            tvRules.movementMethod = LinkMovementMethod.getInstance()
            val color = context?.let { ContextCompat.getColor(it, R.color.primary_purple) }

            val actionInc : () -> Unit = {
                Intent(Intent.ACTION_VIEW,yt.toUri()).run {
                    context?.startActivity(this)
                }
            }
            val actionPolicy : () -> Unit = {
                Intent(Intent.ACTION_VIEW,ig.toUri()).run {
                    context?.startActivity(this)
                }
            }
            if (color != null){
                tvRules.text = getString(R.string.string_helper_rules)
                    .toSpannableHyperLink(
                        resources.configuration.locales[0].language,
                        color,
                        actionInc,
                        actionPolicy
                    )
            }
        }
    }

    private fun isFormValid(): Boolean {
        val email = binding.etEmailEditRegister.text.toString().trim()
        val password = binding.etPasswordEditRegister.text.toString().trim()

        return validateEmail(email) &&
                validatePassword(password)
    }

    private fun validateEmail(email: String) : Boolean {
        return if(email.isEmpty() || emailPattern.matcher(email).matches() || email.length <= 5 ){
            with(binding){
                etEmailInput.isErrorEnabled = false
                etEmailInput.isHelperTextEnabled = true
                etEmailInput.helperText = getString(R.string.string_helper_email)
            }
            false
        } else{
            with(binding){
                etEmailInput.error = getString(R.string.string_email_tidak_valid)
                etEmailInput.isHelperTextEnabled = false
            }
            true
        }
    }
    private fun validatePassword(password: String) : Boolean {
        with(binding){
            return if (passwordPattern.matcher(password).matches() ||password.length <= 5) {
                etPasswordInput.isErrorEnabled = true
                etPasswordInput.isHelperTextEnabled = true
                etPasswordInput.helperText = getString(R.string.string_helper_password)
                true
            } else {
                etPasswordInput.error = getString(R.string.string_helper_password)
                etPasswordInput.isHelperTextEnabled = false
                etPasswordInput.isErrorEnabled = false
                false
            }
        }
    }
}