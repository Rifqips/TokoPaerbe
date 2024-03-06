package id.rifqipadisiliwangi.tokopaerbe.presentation.feature.checkout

import android.content.res.ColorStateList
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import id.rifqipadisiliwangi.core.data.network.api.model.fulfillment.FulfillmentItem
import id.rifqipadisiliwangi.core.domain.model.checkout.ListCheckout
import id.rifqipadisiliwangi.core.domain.model.fulfillment.FulfillmentRequest
import id.rifqipadisiliwangi.core.domain.model.transaction.ItemTransaction
import id.rifqipadisiliwangi.core.domain.model.transaction.TransactionDataClass
import id.rifqipadisiliwangi.core.utils.proceedWhen
import id.rifqipadisiliwangi.tokopaerbe.R
import id.rifqipadisiliwangi.tokopaerbe.databinding.FragmentCheckoutProductBinding
import id.rifqipadisiliwangi.tokopaerbe.presentation.common.adapter.checkout.CheckoutAdapter
import id.rifqipadisiliwangi.tokopaerbe.presentation.feature.receipt.ReceiptPaymentFragmentArgs
import id.rifqipadisiliwangi.tokopaerbe.utils.Constant.zero_value
import id.rifqipadisiliwangi.tokopaerbe.utils.Helper.toCurrencyFormat
import id.rifqipadisiliwangi.tokopaerbe.utils.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CheckoutProductFragment :
    BaseFragment<FragmentCheckoutProductBinding, CheckoutViewModel>(FragmentCheckoutProductBinding::inflate){

    override val viewModel: CheckoutViewModel by viewModel()
    private val args: CheckoutProductFragmentArgs by navArgs()
    private var image: String? = null
    private var label: String? = null
    private lateinit var listProductFulfillment: ArrayList<FulfillmentItem>
    private var productCheckout: ListCheckout = ListCheckout(emptyList())
    private var items: ItemTransaction = ItemTransaction(emptyList())
    private lateinit var itemTransaction: ArrayList<TransactionDataClass>
    private var resultPriceCheckout = 0
    private val adapterCheckoutAdapter by lazy { CheckoutAdapter(productCheckout.listCheckout,
        itemPrice = {
            resultPrice ->
            resultPriceCheckout += resultPrice
            binding.tvTotalPayment.text = resultPriceCheckout.toCurrencyFormat()
        },
        itemSelectedPlus = {
            item ->
            resultPriceCheckout += item.productPrice
            binding.tvTotalPayment.text = resultPriceCheckout.toCurrencyFormat()
        }, itemSelectedMinus = {
            item ->
            resultPriceCheckout -= item.productPrice
            binding.tvTotalPayment.text = resultPriceCheckout.toCurrencyFormat()
        }) }

    override fun initView() {
        productCheckout = args.listCheckoutItem
        setUpRv()
        with(binding) {
            menuToolbar.title = getString(R.string.string_checkout)
            tvTitle.text = getString(R.string.string_pembayaran)
            tvPaymentMethods.text = getString(R.string.string_metode_pembayaran)
            btnBuy.text = getString(R.string.string_beli_langsung)
            tvTotalPayment.text = zero_value.toCurrencyFormat()
            tvTitleTotalPayment.text = resources.getString(R.string.string_total)
            setFragmentResultListener("choosen_method") { _, bundle ->
                if (bundle.isEmpty){
                    val color = ContextCompat.getColor(requireContext(), R.color.grey)
                    btnBuy.backgroundTintList = ColorStateList.valueOf(color)
                    btnBuy.isEnabled = false
                }else{
                    val color = ContextCompat.getColor(requireContext(), R.color.primary_purple)
                    btnBuy.backgroundTintList = ColorStateList.valueOf(color)
                    btnBuy.isEnabled = true
                }
                image = bundle.getString("image").toString()
                label = bundle.getString("label").toString()
                ivAddCard.load(image)
                tvPaymentMethods.text = label
            }
        }
    }

    override fun initListener() {
        with(binding) {
            menuToolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
                resultPriceCheckout = 0
                productCheckout = ListCheckout(emptyList())

            }
            consPaymentMethods.setOnClickListener {
                findNavController().navigate(R.id.action_checkoutProductFragment_to_paymentMethodsFragment)
            }
            btnBuy.setOnClickListener {
                fullfilmentParse()
                observePayment()
            }
        }
    }

    private fun setUpRv() {
        binding.rvCheckout.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapterCheckoutAdapter
        }
    }


    private fun fullfilmentParse() {
        listProductFulfillment = ArrayList()
        productCheckout.listCheckout.map {
            val product = FulfillmentItem(
                it.productId,
                it.productName,
                it.productQuantity
            )
            listProductFulfillment.add(product)
        }
    }


    private fun observePayment() {
        val dataReq = FulfillmentRequest(label!!, listProductFulfillment)
        viewModel.postFulfillment(dataReq)
        viewModel.fulfillmentResult.observe(viewLifecycleOwner) {
            with(binding) {
                it.proceedWhen(
                    doOnLoading = {
                        pbFulfillment.isGone = false
                    },
                    doOnSuccess = {
                        pbFulfillment.isGone = false
                        itemTransaction = ArrayList()
                        val invoiceId = it.payload?.data?.invoiceId
                        val statusValue = resources.getString(R.string.string_success)
                        val tanggalValue = it.payload?.data?.date
                        val waktuValue = it.payload?.data?.time
                        val metodePembayaranValue = it.payload?.data?.payment
                        val totalPembayaranValue = it.payload?.data?.total
                        val product = TransactionDataClass(
                            invoiceId.orEmpty(),
                            statusValue,
                            tanggalValue.orEmpty(),
                            waktuValue.orEmpty(),
                            metodePembayaranValue.orEmpty(),
                            totalPembayaranValue ?: 0
                        )

                        itemTransaction.add(product)
                        items = ItemTransaction(itemTransaction)

                        findNavController().navigate(
                            R.id.action_checkoutProductFragment_to_receiptPaymentFragment,
                            ReceiptPaymentFragmentArgs(items.itemTransaction[0], 0).toBundle(),
                            navOptions = null
                        )
                        Toast.makeText(context, resources.getString(R.string.string_success_create_order), Toast.LENGTH_SHORT).show()
                    },
                    doOnError = {
                        pbFulfillment.isGone = false
                        Toast.makeText(context,resources.getString(R.string.string_failed_create_order), Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}