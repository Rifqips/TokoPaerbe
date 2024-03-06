package id.rifqipadisiliwangi.tokopaerbe.presentation.feature.login

import android.content.Intent
import android.text.method.LinkMovementMethod
import android.view.Gravity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.isGone
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import id.rifqipadisiliwangi.core.domain.model.login.LoginRequest
import id.rifqipadisiliwangi.core.utils.proceedWhen
import id.rifqipadisiliwangi.tokopaerbe.R
import id.rifqipadisiliwangi.tokopaerbe.databinding.FragmentLoginBinding
import id.rifqipadisiliwangi.tokopaerbe.presentation.customview.CustomView
import id.rifqipadisiliwangi.tokopaerbe.utils.Constant.ig
import id.rifqipadisiliwangi.tokopaerbe.utils.Constant.yt
import id.rifqipadisiliwangi.tokopaerbe.utils.Helper.emailPattern
import id.rifqipadisiliwangi.tokopaerbe.utils.Helper.passwordPattern
import id.rifqipadisiliwangi.tokopaerbe.utils.Helper.toSpannableHyperLink
import id.rifqipadisiliwangi.tokopaerbe.utils.TextWatcherConfiguration
import id.rifqipadisiliwangi.tokopaerbe.utils.base.BaseFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment :
    BaseFragment<FragmentLoginBinding, LoginViewModel>(FragmentLoginBinding::inflate) {

    override val viewModel: LoginViewModel by viewModel()
    private var firebaseToken = ""

    override fun initView() {
        setUpSpannable()
        fcmFetch()
    }

    override fun initListener() {
        with(binding){
            btnRegister.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
            etEmailEditLogin.addTextChangedListener(TextWatcherConfiguration(5) { email ->
                validateEmail(email)
            })
            etPasswordEditLogin.addTextChangedListener(TextWatcherConfiguration(5) { password ->
                validatePassword(password)
            })
            btnLogin.setOnClickListener {
                doLogin()
                observeData()
            }
            menuToolbar.title = getString(R.string.string_masuk)
            etEmailInput.hint = getString(R.string.string_email)
            etEmailInput.helperText = getString(R.string.string_helper_email)
            etPasswordInput.helperText = getString(R.string.string_helper_password)
            etPasswordInput.hint = getString(R.string.string_password)
            btnLogin.text = getString(R.string.string_masuk)
            tvHelperRegister.text = getString(R.string.string_helper_register)
            btnRegister.text = getString(R.string.string_daftar)
        }
    }

    private fun observeData() {
        viewModel.loginResult.observe(viewLifecycleOwner){
            it.proceedWhen(
                doOnSuccess = {
                    binding.pbLogin.isGone = true
                    dialogSuccess()

                },
                doOnLoading = {
                    binding.pbLogin.isGone = false
                },
                doOnError = {
                    binding.pbLogin.isGone = true
                    dialogFailed()
                }
            )
        }
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


    private fun fcmFetch(){
        Firebase.messaging.token.addOnCompleteListener(
            OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }
                val token = task.result
                firebaseToken = token
            },
        )
    }
    private fun dialogSuccess() {
        val dialog = context?.let { context ->
            CustomView.dialogCustom(
                context,
                Gravity.CENTER,
                R.raw.success
            ) {
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
        }
        dialog?.show()
        lifecycleScope.launch {
            delay(3000)
        }
        dialog?.dismiss()
    }

    private fun dialogFailed() {
        val dialog = context?.let { context ->
            CustomView.dialogCustom(
                context,
                Gravity.CENTER,
                R.raw.failed
            ) {}
        }
        dialog?.show()
        lifecycleScope.launch {
            delay(3000)
            dialog?.dismiss()
        }
    }

    private fun isFormValid(): Boolean {
        val email = binding.etEmailEditLogin.text.toString().trim()
        val password = binding.etPasswordEditLogin.text.toString().trim()

        return validateEmail(email) &&
                validatePassword(password)
    }

    private fun doLogin() {
        val email = binding.etEmailEditLogin.text.toString().trim()
        val password = binding.etPasswordEditLogin.text.toString().trim()
        val firebaseToken = ""

        val userAuth = LoginRequest(
            email,
            password,
            firebaseToken,
        )
        viewModel.userLogin(userAuth)
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
    override fun onResume() {
        super.onResume()
        viewModel.debugScreenView(getString(R.string.string_masuk))
    }
}



