package id.rifqipadisiliwangi.tokopaerbe.presentation.feature.receipt

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import id.rifqipadisiliwangi.core.domain.model.rating.RatingRequest
import id.rifqipadisiliwangi.core.domain.model.transaction.TransactionDataClass
import id.rifqipadisiliwangi.core.utils.proceedWhen
import id.rifqipadisiliwangi.tokopaerbe.R
import id.rifqipadisiliwangi.tokopaerbe.databinding.FragmentReceiptPaymentBinding
import id.rifqipadisiliwangi.tokopaerbe.presentation.feature.store.StoreListViewModel
import id.rifqipadisiliwangi.tokopaerbe.utils.Helper.toCurrencyFormat
import id.rifqipadisiliwangi.tokopaerbe.utils.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReceiptPaymentFragment : BaseFragment<FragmentReceiptPaymentBinding, StoreListViewModel>(FragmentReceiptPaymentBinding::inflate) {

    override val viewModel: StoreListViewModel by viewModel()
    private val args: ReceiptPaymentFragmentArgs? by navArgs()
    private var size: Int = 0
    private var itemTransaction: TransactionDataClass? = null

    override fun initView() {
        itemTransaction = args?.item
        size = args?.size!!
        with(binding){
            menuToolbar.title = getString(R.string.string_status)
            tvTitleReceipt.text = getString(R.string.string_pembayaran_berhasil)
            tvReviewProduct.text = getString(R.string.string_pembayaran_berhasil)
            etReviews.hint = getString(R.string.string_beri_ulasan_product)
            tvTitleId.text = getString(R.string.id_transaksi)
            tvTitleStatus.text = getString(R.string.string_status)
            tvTitleDate.text = getString(R.string.string_tanggal)
            tvTitleTime.text = getString(R.string.string_waktu)
            tvTitlePaymentMethods.text = getString(R.string.string_metode_pembayaran)
            tvTitleTotal.text = getString(R.string.string_total)
            btnDone.text = getString(R.string.string_selesai)
            ivApprove.load(R.drawable.ic_approve)
            tvContentId.text = itemTransaction?.invoiceId
            tvContentStatus.text = itemTransaction?.StatusValue
            tvContentDate.text = itemTransaction?.tanggalValue
            tvContentTime.text = itemTransaction?.waktuValue
            tvContentPaymentMethods.text = itemTransaction?.metodePembayaranValue
            tvContentTotal.text = itemTransaction?.totalPembayaranValue?.toCurrencyFormat()
        }
    }

    override fun initListener() {
        with(binding){
            btnDone.setOnClickListener {
                observeData()
            }
        }
    }

    private fun observeData(){
        val userRating = RatingRequest(itemTransaction?.invoiceId, binding.ratingProduct.rating.toInt().toString().trim(), binding.etReviews.text.toString().trim())
        showToast("Rating: ${binding.ratingProduct.rating}")
        viewModel.userRating(userRating)
        viewModel.userRating.observe(viewLifecycleOwner){
            it.proceedWhen (
                doOnLoading = {
                    binding.layoutStateReceiptd.pbLoading.isVisible = true
                },
                doOnSuccess = {
                    binding.layoutStateReceiptd.pbLoading.isVisible = false
                    findNavController().navigate(R.id.action_receiptPaymentFragment_to_homeFragment)
                },
                doOnError = {
                    showToast("Failure Create Review")
                },
            )
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}