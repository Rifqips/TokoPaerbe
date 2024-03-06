package id.rifqipadisiliwangi.tokopaerbe.presentation.feature.payment

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.gson.Gson
import id.rifqipadisiliwangi.core.domain.model.payment.PaymentResponse
import id.rifqipadisiliwangi.tokopaerbe.R
import id.rifqipadisiliwangi.tokopaerbe.databinding.FragmentPaymentMethodsBinding
import id.rifqipadisiliwangi.tokopaerbe.presentation.common.adapter.payment.choose.ChoosePaymentAdapter
import id.rifqipadisiliwangi.tokopaerbe.presentation.common.adapter.payment.method.PaymentMethodsAdapter
import id.rifqipadisiliwangi.tokopaerbe.utils.Helper.toJson
import id.rifqipadisiliwangi.tokopaerbe.utils.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class PaymentMethodsFragment : BaseFragment<FragmentPaymentMethodsBinding, PaymentViewModel>(FragmentPaymentMethodsBinding::inflate), PaymentMethodsAdapter.OnItemClickListener {


    override val viewModel: PaymentViewModel by viewModel()
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var jsonModel: PaymentResponse
    val adapter = ChoosePaymentAdapter(this)
    private var clickedLabel: String? = null
    private var clickedImage: String? = null
    override fun initView() {
        observeData()
        with(binding){
            menuToolbar.title = getString(R.string.string_pembayaran)
            menuToolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        firebaseAnalytics = FirebaseAnalytics.getInstance(requireContext())
        firebaseAnalytics.logEvent("payment_list", Bundle().apply { putString("screenName", getString(R.string.string_pilih_pembayaran)) })
    }

    private fun observeData(){

        viewModel.fetchData()
        viewModel.fetchUpdate()
        with(binding){
            rvPayment.layoutManager = LinearLayoutManager(requireContext())
            viewModel.remoteResult.observe(viewLifecycleOwner){ paymentResult ->
                val gson = Gson()
                if (paymentResult.toJson().isNotEmpty()) {
                    jsonModel = gson.fromJson(paymentResult.toJson(), PaymentResponse::class.java)
                    showLoading(false)
                    adapter.submitList(jsonModel.data)
                    rvPayment.adapter = adapter
                }
            }
            viewModel.remoteResultUpdate.observe(viewLifecycleOwner){ paymentResult ->
                val gson = Gson()
                if (paymentResult.toJson().isNotEmpty()) {
                    jsonModel = gson.fromJson(paymentResult.toJson(), PaymentResponse::class.java)
                    showLoading(false)
                    adapter.submitList(jsonModel.data)
                    rvPayment.adapter = adapter
                }
            }
        }

    }

    override fun initListener() {}

    override fun onItemClick(image: String, name: String) {
        clickedImage = image
        clickedLabel = name
        val choosenMethod = bundleOf().apply {
            putString("image", clickedImage)
            putString("label", clickedLabel)
        }

        setFragmentResult("choosen_method", choosenMethod)
        findNavController().navigateUp()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}
