package id.rifqipadisiliwangi.tokopaerbe.presentation.feature.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.google.android.material.chip.Chip
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import id.rifqipadisiliwangi.core.data.network.api.model.store.ResponseItemDataStore
import id.rifqipadisiliwangi.core.utils.proceedWhen
import id.rifqipadisiliwangi.tokopaerbe.R
import id.rifqipadisiliwangi.tokopaerbe.databinding.FragmentDetailProductBinding
import id.rifqipadisiliwangi.tokopaerbe.presentation.common.adapter.detail.DetailAdapter
import id.rifqipadisiliwangi.tokopaerbe.utils.Constant.detail_bundle
import id.rifqipadisiliwangi.tokopaerbe.utils.Constant.review_bundle
import id.rifqipadisiliwangi.tokopaerbe.utils.Helper.toCurrencyFormat
import id.rifqipadisiliwangi.tokopaerbe.utils.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailProductFragment :
    BaseFragment<FragmentDetailProductBinding, DetailViewModel>(FragmentDetailProductBinding::inflate){

    override val viewModel: DetailViewModel by viewModel()

    private lateinit var vpOnboarding: ViewPager2
    private lateinit var adapter: DetailAdapter
    private lateinit var tabs: TabLayout
    private var productId = ""

    override fun initView() {
        getBundle()
        observeData()
        with(binding){
            tvTitleReviewUsers.text = resources.getString(R.string.string_ulasan_pembeli)
            tvTitleReviewUsers.text = resources.getString(R.string.string_ulasan_pembeli)
            tvShowAllRate.text = resources.getString(R.string.string_lihat_semua)
            tvAllReviews.text = resources.getString(R.string.string_100_pembeli_merasa_puas)
            btnOrderNow.text = resources.getString(R.string.string_beli_langsung)
            btnCart.text = resources.getString(R.string.string_cart)
            tvTitleVarian.text = resources.getString(R.string.string_pilih_varian)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observeData(){
        viewModel.detailStore.observe(viewLifecycleOwner){ it ->
            it.proceedWhen (
                doOnLoading = {
                    binding.layoutStateDetail.pbLoading.isVisible = true
                    binding.layoutStateDetail.tvError.isVisible = false
                },
                doOnSuccess = {
                    binding.layoutStateDetail.pbLoading.isVisible = false
                    binding.layoutStateDetail.tvError.isVisible = true
                    it.payload.let {
                        with(binding){
                            tvDetailPrice.text = it?.productPrice?.toCurrencyFormat()
                            tvDetailResultDescription.text = "${resources.getString(R.string.string_hasil_penjualan)} ${it?.sale.toString()}"
                            tvDetailDescription.text = it?.productName
                            tvDescriptionProduct.text = it?.brand
                            tvResultDescriptionProduct.text = it?.description
                            productId = it?.productId.toString()
                            tvRateUsers.text = it?.productRating.toString()
                            tvResultRate.text = "${it?.productRating} (${it?.totalReview})"
                            tvReviewsDescription.text = "${it?.totalRating} rating ${it?.totalReview} review"
                            svDetailStore.isVisible = true
                            if (it != null) {
                                setUpvp(it.image)
                                tabs.isVisible = it.image.size > 1
                            }
                            it?.productVariant?.forEach { chipItem ->
                                val chip = Chip(context)
                                chip.text = chipItem.variantName
                                chip.isClickable = true
                                chip.isCheckable = true
                                cgVarian.addView(chip)
                                chip.setOnCheckedChangeListener { _, isChecked ->
                                    when (isChecked) {
                                        true -> {
                                            val priceResult = it.productPrice + chipItem.variantPrice
                                            tvDetailPrice.text = priceResult.toCurrencyFormat()
                                        }
                                        else -> tvDetailPrice.text = it.productPrice.toCurrencyFormat()
                                    }
                                }

                            }
                        }
                        if (it != null) {
                            addWishlist(
                                it.productId,
                                it.productName,
                                it.productPrice,
                                it.image[0],
                                it.store,
                                it.productRating.toFloat(),
                                it.sale,
                                it.stock,
                                it.productVariant.toString(),
                                1
                            )
                            getFavorite(it.productId)
                            getCartById(it.productId)
                            addCartList(
                                it.productId,
                                it.productName,
                                it.productVariant.map { it.variantName }.toString(),
                                it.stock,
                                it.productPrice.toString().toInt(),
                                1,
                                it.image[0],
                                false
                            )
                        }
                    }
                }
            )
        }
    }

    @SuppressLint("InflateParams")
    private fun setUpvp(imageUrls : List<String>) {
        vpOnboarding = binding.vpDetailProduct
        tabs = binding.tabs
        adapter = DetailAdapter(imageUrls)
        vpOnboarding.adapter = adapter

        TabLayoutMediator(
            tabs,
            vpOnboarding
        ) { tab, _ ->
            tab.customView = layoutInflater.inflate(R.layout.custom_tab_layout, null)
        }.attach()
    }

    override fun initListener() {
        with(binding) {
            menuToolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            tvShowAllRate.setOnClickListener {
                navigateToDetail(reviewDetail = ResponseItemDataStore(productId = productId))
            }
        }
    }
    private fun addWishlist(
        id: String,
        productName: String,
        productPrice: Int,
        image: String,
        store: String,
        productRating: Float,
        sale: Int,
        stock: Int,
        variantName: String,
        quantity: Int
    ){
        binding.ivDetailFavorite.setOnClickListener {
            viewModel.addWishlist(id, productName, productPrice, image, store, productRating, sale, stock, variantName, quantity)
            Toast.makeText(context, resources.getString(R.string.string_success_added_to_wishlist), Toast.LENGTH_SHORT).show()
            binding.ivDetailFavorite.load(R.drawable.ic_favorite_filled)
        }
    }

    private fun addCartList(
        id: String,
        productName: String,
        variantName: String,
        stock: Int,
        productPrice: Int,
        quantity: Int,
        image: String,
        isChecked: Boolean,
        ){
        binding.btnCart.setOnClickListener {
            viewModel.addCartList(id, productName, variantName, stock, productPrice, quantity, image, isChecked)
            Toast.makeText(context, resources.getString(R.string.string_success_added_to_Cart), Toast.LENGTH_SHORT).show()
        }
    }

    private fun getFavorite(id: String){
        viewModel.checkWishlistFavorite(id)
        viewModel.getIsFavorite.observe(viewLifecycleOwner){
            with(binding){
                when(it.size){
                    1 -> {
                        ivDetailFavorite.load(R.drawable.ic_favorite_filled)
                        ivDetailFavorite.isClickable = false
                    }
                    else ->{
                        ivDetailFavorite.load(R.drawable.ic_favorite)
                        ivDetailFavorite.isClickable = true
                    }
                }
            }
        }
    }
    @SuppressLint("ResourceAsColor")
    private fun getCartById(productId : String){
        viewModel.getChartById(productId)
        viewModel.chartById.observe(viewLifecycleOwner){
            with(binding){
                when(it.size){
                    1 ->  btnCart.isEnabled = false
                    else -> btnCart.isEnabled = true
                }
            }
        }
    }

    private fun getBundle(){
        arguments?.let {
            it.getParcelable<ResponseItemDataStore>(detail_bundle)?.let { detail ->
                val productId = detail.productId
                if (productId != null) {
                    viewModel.detailStore(productId = productId)
                }
            }
        }
    }


    private fun navigateToDetail(reviewDetail : ResponseItemDataStore){
        val bundle = Bundle()
        bundle.putParcelable(review_bundle, reviewDetail)
        findNavController().navigate(R.id.action_detailProductFragment_to_reviewProductFragment, bundle)
    }
}