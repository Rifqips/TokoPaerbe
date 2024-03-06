package id.rifqipadisiliwangi.tokopaerbe.presentation.feature.cart

import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import id.rifqipadisiliwangi.core.domain.model.checkout.CheckoutDataClass
import id.rifqipadisiliwangi.core.domain.model.checkout.ListCheckout
import id.rifqipadisiliwangi.tokopaerbe.R
import id.rifqipadisiliwangi.tokopaerbe.databinding.FragmentCartBinding
import id.rifqipadisiliwangi.tokopaerbe.presentation.common.adapter.cart.CartAdapter
import id.rifqipadisiliwangi.tokopaerbe.presentation.feature.checkout.CheckoutProductFragmentArgs
import id.rifqipadisiliwangi.tokopaerbe.utils.Constant
import id.rifqipadisiliwangi.tokopaerbe.utils.Helper.toCurrencyFormat
import id.rifqipadisiliwangi.tokopaerbe.utils.base.BaseFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment : BaseFragment<FragmentCartBinding, CartViewModel>(FragmentCartBinding::inflate) {

    override val viewModel: CartViewModel by viewModel()

    private lateinit var adapterCart : CartAdapter
    private lateinit var listCheckout: ArrayList<CheckoutDataClass>
    private var productCheckout: ListCheckout = ListCheckout(emptyList())

    private var resultPrice = 0

    override fun initView(){
        listCheckout = ArrayList()
        observeData()
        setUpAdapter()
        initLoading()
        with(binding){
            cbAddAll.text = getString(R.string.string_pilih_semua)
            menuToolbar.title = getString(R.string.string_cart)
            tvDeleteAll.text = getString(R.string.string_delete)
            tvTotalPayment.text = Constant.zero_value.toCurrencyFormat()
            tvTitleTotalPayment.text = getString(R.string.string_total_bayar)
            btnBuy.text = getString(R.string.string_beli_langsung)
            rvCart.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = adapterCart
            }
            rvCart.postDelayed({
                binding.rvCart.smoothScrollToPosition(0)
            }, 1000)
        }
    }

    override fun initListener() {
        with(binding) {
            menuToolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
                resetPrice()
            }
            cbAddAll.setOnCheckedChangeListener { _, isChecked ->
                initLoading()
                viewModel.updateBooleanColumnAll(isChecked)
                when (isChecked){
                    true -> tvDeleteAll.setOnClickListener {
                        initLoading()
                        cbAddAll.isChecked = false
                        viewModel.deleteAllcart()
                    }
                    false ->  tvDeleteAll.isClickable = false
                }
            }
        }
    }

    private fun setUpAdapter(){
        with(binding){
            adapterCart = CartAdapter(emptyList(),
                itemSelected = { item, isChecked ->
                    initLoading()
                    viewModel.isChecked(item.productId, isChecked)
                    when (isChecked){
                        true -> {
                            btnBuy.isEnabled = true
                            val color = ContextCompat.getColor(requireContext(), R.color.primary_purple)
                            btnBuy.backgroundTintList = ColorStateList.valueOf(color)
                            resultPrice += item.productPrice
                            tvTotalPayment.text  = resultPrice.toCurrencyFormat()
                        }
                        false -> {
                            val color = ContextCompat.getColor(requireContext(), R.color.grey)
                            btnBuy.backgroundTintList = ColorStateList.valueOf(color)
                            btnBuy.isEnabled = false
                            resultPrice -= item.productPrice
                            tvTotalPayment.text  = resultPrice.toCurrencyFormat()
                        }
                    }
                },
                itemSelectedPlus = { cartKeys ->
                    run {
                        initLoading()
                        resultPrice += cartKeys.productPrice
                        tvTotalPayment.text  = resultPrice.toCurrencyFormat()
                    }
                },
                itemSelectedMinus = {
                        cartKeys->
                    run {
                        initLoading()
                        resultPrice -= cartKeys.productPrice
                        tvTotalPayment.text  = resultPrice.toCurrencyFormat()
                    }
                },
                itemDelete = {
                        cartKeys, item->
                    run {
                        initLoading()
                        resultPrice -= item
                        tvTotalPayment.text  = resultPrice.toCurrencyFormat()
                        viewModel.deleteCartById(cartKeys.productId)
                    }
                }
            )
        }
    }

    private fun initLoading(){
        isInvisibilePrice()
        lifecycleScope.launch {
            delay(2000)
            isVisibilePrice()
        }
    }
    private fun isVisibilePrice(){
        with(binding){
            shimmerCartPrice.isGone = true
            tvTotalPayment.isVisible = true
            tvTitleTotalPayment.isVisible = true
        }
    }

    private fun isInvisibilePrice(){
        with(binding){
            shimmerCartPrice.isGone = false
            tvTotalPayment.isVisible = false
            tvTitleTotalPayment.isVisible = false
        }
    }

    private fun observeData(){
        viewModel.getCartList()
        viewModel.cartList.observe(viewLifecycleOwner){cartList ->
            adapterCart.setlistCart(cartList)
            if (cartList?.isNotEmpty() == true) {
                val selectedItem = cartList.filter { it.isChecked }
                binding.btnBuy.setOnClickListener {
                    val selectedCheckoutList = selectedItem.map { it ->
                        val productId = it.productId
                        val productImage = it.image
                        val productName = it.productName
                        val productVariant = it.variantName
                        val productStock = it.stock
                        val productPrice = it.productPrice
                        val productQuantity = it.quantity
                        CheckoutDataClass(
                            productId,
                            productImage,
                            productName,
                            productVariant,
                            productStock,
                            productPrice,
                            productQuantity
                        )
                    }
                    listCheckout.addAll(selectedCheckoutList)
                    productCheckout = ListCheckout(listCheckout)
                    findNavController().navigate(
                        R.id.action_cartFragment_to_checkoutProductFragment,
                        CheckoutProductFragmentArgs(productCheckout).toBundle(),
                        navOptions = null
                    )
                    resetPrice()
                }

            }
        }
    }

    private fun resetPrice(){
        binding.cbAddAll.isChecked = false
        resultPrice = 0
        viewModel.updateBooleanColumnAll(false)
    }

}